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

export default function ProcessFlowEditor({ processFlowId, onCancel }) {
   const { data: configData } = useApi({
      url: `process-flows/${processFlowId}/graph`,
   });
   const [stepData, setStepData] = useState([])
   const [nodes, setNodes, onNodesChange] = useNodesState([]);
   const [edges, setEdges, onEdgesChange] = useEdgesState([]);

   useEffect(() => {
      if (!configData) return;

      const { statuses = [], steps = [] } = configData.data;
      setStepData(steps)

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

   // Placeholder API call
   const updateStepApi = async (stepId, patchBody) => {
      console.log("API call: update step", stepId, patchBody);
      return Promise.resolve({ ok: true });
   };

   const onConnectHandler = useCallback(

      async (connection) => {
         const { source, target } = connection;
         const sourceId = Number(source);
         const targetId = Number(target);
         console.log(connection)


         const targetStep = stepData.find((s) => s.id === targetId);
         if (!targetStep) return;

         const newEndIdForA = targetStep.startProcessStatusId ?? null;
         console.log(sourceId, targetId)

         try {
            await updateStepApi(sourceId, { endProcessStatusId: newEndIdForA });
         } catch (err) {
            console.error("API update failed", err);
            return;
         }

         // Update local steps
         setSteps((prev) =>
            prev.map((s) =>
               s.id === sourceId ? { ...s, endProcessStatusId: newEndIdForA } : s
            )
         );

         setEdges((prev) => [
            ...prev,
            { id: `e-${source}-${target}`, source, target, animated: true }
         ]);


         // Recompute layout
         setNodes((prevNodes) => {
            const updatedNodes = prevNodes.map((n) => {
               const s = steps.find((st) => String(st.id) === n.id) || n.data.step;
               return { ...n, data: { ...n.data, step: s } };
            });
            return getDagreLayout(updatedNodes, [
               ...edges,
               { id: `e-${source}-${target}`, source, target },
            ]);
         });
      },
      [edges]
   );

   return (
      <Modal
         title="Config step"
         destroyOnHidden
         open={true}
         maskClosable={false}
         onCancel={onCancel}
         width="80%"
      >
         <div style={{ width: "100%", height: "650px" }}>
            <ReactFlow
               nodes={nodes}
               edges={edges}
               onNodesChange={onNodesChange}
               onEdgesChange={onEdgesChange}
               onConnect={onConnectHandler}
               fitView
            >
               <Background />
               <Controls />
            </ReactFlow>
         </div>
      </Modal>
   );
}
