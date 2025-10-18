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



const dagreGraph = new dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));
const nodeWidth = 172;
const nodeHeight = 36;

function getDagreLayout(nodes, edges, direction = "LR") {
   dagreGraph.setGraph({ rankdir: direction });
   nodes.forEach((n) =>
      dagreGraph.setNode(n.id, { width: nodeWidth, height: nodeHeight })
   );
   edges.forEach((e) => dagreGraph.setEdge(e.source, e.target));
   dagre.layout(dagreGraph);

   const positioned = nodes.map((n) => {
      const nodeWithPosition = dagreGraph.node(n.id);
      return {
         ...n,
         position: {
            x: nodeWithPosition.x - nodeWidth / 2,
            y: nodeWithPosition.y - nodeHeight / 2,
         },
      };
   });
   return positioned;
}

export default function ProcessFlowEditor({ stepsData, direction = "LR" }) {
   const [steps, setSteps] = useState(() => (stepsData || []));

   const initialNodes = (steps || []).map((s) => ({
      id: String(s.id),
      data: { label: s.name, step: s },
      position: { x: 0, y: 0 },
   }));

   const initialEdges = [];
   for (const a of steps) {
      if (a.endProcessStatusId == null) continue; 
      const b = steps.find((x) => x.startProcessStatusId === a.endProcessStatusId);
      if (b) {
         initialEdges.push({
            id: `e-${a.id}-${b.id}`,
            source: String(a.id),
            target: String(b.id),
            animated: false,
         });
      }
   }

   const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
   const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

   useEffect(() => {
      const positioned = getDagreLayout(initialNodes, initialEdges, direction);
      setNodes(positioned);
      setEdges(initialEdges);
      setSteps(stepsData || []);
   }, [JSON.stringify(stepsData)]); 


   const updateStepApi = async (stepId, patchBody) => {
      return Promise.resolve({ ok: true });
   };


   const onConnectHandler = useCallback(
      async (connection) => {
         const { source, target } = connection; // strings
         const sourceId = Number(source);
         const targetId = Number(target);

         const targetStep = steps.find((s) => s.id === targetId);
         if (!targetStep) return;

         const newEndIdForA = targetStep.startProcessStatusId ?? null;

         // 1) update server (placeholder) - as per your earlier choice we call API immediately
         try {
            await updateStepApi(sourceId, { endProcessStatusId: newEndIdForA });
         } catch (err) {
            console.error("API update failed", err);
            // optionally show toast and abort UI update
            return;
         }

         // 2) update local steps state (so future relations can use updated value)
         setSteps((prev) =>
            prev.map((s) => (s.id === sourceId ? { ...s, endProcessStatusId: newEndIdForA } : s))
         );

         // 3) enforce single outgoing from source: remove existing edges with same source
         setEdges((eds) => {
            const filtered = eds.filter((e) => e.source !== source);
            const newEdge = {
               id: `e-${source}-${target}`,
               source,
               target,
            };
            return [...filtered, newEdge];
         });

         // 4) optional: recompute layout so new edge positions nicely (re-run dagre)
         // rebuild nodes from updated steps to carry fresh step data
         const updatedNodes = nodes.map((n) => {
            const s = (steps.find((st) => String(st.id) === n.id) || n.data.step) || n.data.step;
            return { ...n, data: { ...n.data, step: s } };
         });
         const updatedEdges = (() => {
            // we already setEdges above; but to recompute positions we'll rebuild edges from current edges state
            // here just use current edges state plus new: (simple approach)
            return [...initialEdges]; // optional: keep current edges; to keep it simple skip complex sync
         })();

         const positioned = getDagreLayout(updatedNodes, [...edges, { source, target, id: `e-${source}-${target}` }], direction);
         setNodes(positioned);
      },
      // deps: nodes, edges, steps
      [edges, nodes, steps, direction, updateStepApi, setEdges, setNodes]
   );

   return (
      <div style={{ width: "100%", height: "700px" }}>
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
   );
}
