import React, { useCallback, useEffect, useState } from "react";
import ReactFlow, {
   Background,
   Controls,
   addEdge,
   useNodesState,
   useEdgesState,
} from "reactflow";
import dagre from "dagre";
import "reactflow/dist/style.css";
import { Modal } from "antd";
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

export default function ProcessFlowViewer({ processFlowId, onCancel }) {
   const { data: configData } = useApi({
      url: `process-flows/${processFlowId}/graph`,
   });
   const [nodes, setNodes, onNodesChange] = useNodesState([]);
   const [edges, setEdges, onEdgesChange] = useEdgesState([]);

   useEffect(() => {
      if (!configData) return;

      const { statuses = [], steps = [] } = configData.data;

      const statusNodes = statuses.map((s) => ({
         id: String(s.id),
         data: { label: s.name },
         position: { x: 0, y: 0 },
      }));

      const stepEdges = steps.filter((s) => s.startProcessStatusId && s.endProcessStatusId).map((s) => ({
         id: `e-${s.id}`,
         source: String(s.startProcessStatusId),
         target: String(s.endProcessStatusId),
         label: s.name,
         animated: true
      }))

      const positionedNodes = getDagreLayout(statusNodes, stepEdges);

      setNodes(positionedNodes);
      setEdges(stepEdges);
   }, [configData]);

   return (
      <Modal
         title="View flow"
         destroyOnHidden
         open={true}
         maskClosable={false}
         onCancel={onCancel}
         footer={null}
         width="80%"
      >
         <div style={{ width: "100%", height: "650px" }}>
            <ReactFlow
               nodes={nodes}
               edges={edges}
               onNodesChange={onNodesChange}
               onEdgesChange={onEdgesChange}
               fitView
            >
               <Background />
               <Controls />
            </ReactFlow>
         </div>
      </Modal>
   );
}
