import { useEffect, useState } from "react";
import useApi from "../hooks/useApi";
import DEFAULT_PAGINATION from "../constants/pagination";
import { useParams } from "react-router-dom";
import { BiEdit, BiPlus, BiTrash } from "react-icons/bi";
import { Button, Popconfirm, Space, Table } from "antd";
import BackButton from "../components/BackButton";
import CreateStep from "../partials/processStep/CreateStep";
import Search from "antd/es/input/Search";
import ProcessFlowEditor from './../partials/processStep/ProcessFlowEditor'

export default function ProcessStepManagement() {
   const { id } = useParams();
   const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
   const [isModalOpen, setIsModalOpen] = useState(false);
   const [searchText, setSearchText] = useState("");
   const [currentStep, setCurrentStep] = useState(null);


   const { data, loading, error, refetch } = useApi({
      url: "process-steps/paged",
      params: { page: pagination.page, size: pagination.maxSize, search: searchText, processFlowId: id },
      isAuth: true
   });

   const { refetch: deleteStep } = useApi({
      method: 'DELETE',
      auto: false,
   });

   const handleDelete = async (id) => {
      await deleteStep({ url: 'process-steps/' + id });
      refetch();
   }


   const columns = [
      {
         title: "Name",
         dataIndex: "name",
         key: "name",
      },
      {
         title: "Actions",
         key: "actions",
         render: (_, record) => (
            <Space>
               <Button icon={<BiEdit />} type="link" onClick={() => {
                  setCurrentStep(record)
                  setIsModalOpen(true);
               }}>
               </Button>

               <Popconfirm
                  title={`Bạn có chắc muốn xoá Step "${record.name}"?`}
                  okText="Xoá"
                  cancelText="Huỷ"
                  okType="danger"
                  onConfirm={() => {
                     handleDelete(record.id);
                  }}
               >
                  <Button danger icon={<BiTrash />} />
               </Popconfirm>
            </Space>
         ),
      },
   ];

   useEffect(() => {
      refetch({
         params: { page: pagination.page, size: pagination.size, search: searchText, processFlowId: id },
      });
   }, [searchText]);

   if (error) {
      return <p style={{ color: "red" }}>Error: {error.message}</p>;
   }

   return (
      <div style={{ padding: 16 }}>
         <h1 style={{ fontSize: 20, fontWeight: "bold", marginBottom: 16 }}>
            Step Management
         </h1>
         <BackButton />
         <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
            <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
            <Button
               type="primary"
               onClick={() => {
                  setCurrentStep(null)
                  setIsModalOpen(true)
               }}
               icon={<BiPlus />}
               style={{ marginBottom: 16 }}
            >
            </Button>
         </div>

         <Table
            rowKey="id"
            columns={columns}
            dataSource={data?.data?.items || []}
            loading={loading}
            pagination={false}
            bordered
         />
         {data?.success && data?.data?.items && (
            <ProcessFlowEditor stepsData={data.data.items} />
         )}

         <CreateStep processFlowId={id} refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentStep} />
      </div>
   );
}