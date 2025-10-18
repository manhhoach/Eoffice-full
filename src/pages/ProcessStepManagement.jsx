import { useEffect, useState } from "react";
import useApi from "../hooks/useApi";
import DEFAULT_PAGINATION from "../constants/pagination";
import { useParams } from "react-router-dom";
import { BiCheck, BiEdit, BiPlus, BiTrash, BiX } from "react-icons/bi";
import { Button, Popconfirm, Space, Table } from "antd";
import BackButton from "../components/BackButton";
import CreateStep from "../partials/processStep/CreateStep";
import Search from "antd/es/input/Search";

export default function ProcessStepManagement() {
   const { id } = useParams();
   const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
   const [isModalOpen, setIsModalOpen] = useState(false);
   const [searchText, setSearchText] = useState("");
   const [currentStep, setCurrentStep] = useState(null);


   const { data, loading, error, refetch } = useApi({
      url: "process-steps/paged",
      params: { page: pagination.current, size: pagination.pageSize, search: searchText, processFlowId: id },
   });
   console.log(data)

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
         title: "Is Start",
         dataIndex: "isStart",
         key: "isStart",
         render: (value) =>
            value ? (
               <span style={{ color: "green" }}>
                  <BiCheck />
               </span>
            ) : (
               <span style={{ color: "red" }}>
                  <BiX />
               </span>
            ),
      },
      {
         title: "Is End",
         dataIndex: "isEnd",
         key: "isEnd",
         render: (value) =>
            value ? (
               <span style={{ color: "green" }}>
                  <BiCheck />
               </span>
            ) : (
               <span style={{ color: "red" }}>
                  <BiX />
               </span>
            ),
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

   const handleTableChange = (newPagination) => {
      setPagination(newPagination);
      refetch({
         params: { page: newPagination.page, size: newPagination.size, search: searchText, moduleId: id },
      });
   };

   useEffect(() => {
      refetch({
         params: { page: pagination.page, size: pagination.size, search: searchText, moduleId: id },
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
            pagination={{
               current: data?.data?.currentPage || pagination.current,
               pageSize: pagination.pageSize,
               total: data?.data?.totalElements || 0,
               showSizeChanger: true,
            }}
            onChange={handleTableChange}
            bordered
         />

         <CreateStep processFlowId={id} refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentStep} />
      </div>
   );
}