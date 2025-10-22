import React, { useCallback, useEffect, useState } from "react";
import ReactFlow, {
   Background,
   Controls,
   addEdge,
   useNodesState,
   useEdgesState,
   applyNodeChanges,
} from "reactflow";
import dagre from "dagre";
import "reactflow/dist/style.css";
import { message, Modal } from "antd";
import useApi from "../../hooks/useApi";

const dagreGraph = new dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));
const nodeWidth = 172;
const nodeHeight = 36;

function getDagreLayout(nodes, edges) {
   dagreGraph.setGraph({ rankdir: "LR" });
   nodes.forEach((n) =>
      dagreGraph.setNode(n.id, { width: nodeWidth, height: nodeHeight })
   );
   edges.forEach((e) => dagreGraph.setEdge(e.source, e.target));
   dagre.layout(dagreGraph);

   return nodes.map((n) => {
      const nodeWithPosition = dagreGraph.node(n.id);
      return {
         ...n,
         position: {
            x: nodeWithPosition.x - nodeWidth / 2,
            y: nodeWithPosition.y - nodeHeight / 2,
         },
      };
   });
}

export default function ProcessFlowViewer({ steps, statuses, updatePosition }) {

   const [nodes, setNodes, onNodesChange] = useNodesState([]);
   const [edges, setEdges, onEdgesChange] = useEdgesState([]);

   useEffect(() => {
      const statusNodes = statuses.map((s) => ({
         id: String(s.id),
         data: { label: s.name },
         position: { x: s.x ?? 0, y: s.y ?? 0 },
      }));
      const stepEdges = steps.filter((s) => s.startProcessStatusId && s.endProcessStatusId).map((s) => ({
         id: `e-${s.id}`,
         source: String(s.startProcessStatusId),
         target: String(s.endProcessStatusId),
         label: s.name,
         animated: true
      }))
      const needLayout = statusNodes.some(n => n.position.x === 0 && n.position.y === 0);
      const finalNodes = needLayout ? getDagreLayout(statusNodes, stepEdges) : statusNodes;

      setNodes(finalNodes);
      setEdges(stepEdges);
   }, [steps, statuses]);

   const handleNodeDragStop = async (event, node) => {
      const position = {
         id: node.id,
         x: node.position.x,
         y: node.position.y
      }
      await updatePosition({ body: position })
      message.success("Process Flow update successfully!");
   }

   return (
      <div style={{ width: "100%", height: "650px" }}>
         <ReactFlow
            nodes={nodes}
            edges={edges}
            onNodesChange={onNodesChange}
            onEdgesChange={onEdgesChange}
            onNodeDragStop={handleNodeDragStop}
            fitView
         >
            <Background />
            <Controls />
         </ReactFlow>
      </div>
   );
}
