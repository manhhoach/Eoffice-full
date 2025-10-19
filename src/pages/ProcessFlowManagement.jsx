import { useEffect, useState } from "react";
import { Table, Button, Space, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi";
import CreateProcessFlow from "../partials/processFlow/CreateProcessFlow.jsx";
import { BiEdit, BiPlus, BiTrash, BiCog, BiListUl, BiSignal5 } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination";
import { useNavigate } from "react-router-dom";
import ProcessFlowEditor from "../partials/processStep/ProcessFlowEditor.jsx";
const { Search } = Input;

export default function ProcessFlowManagement() {
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isModalConfigOpen, setIsModalConfigOpen] = useState(false);
    const [searchText, setSearchText] = useState("");
    const [currentProcessFlow, setCurrentProcessFlow] = useState(null);
    const navigate = useNavigate();
    const { data, loading, error, refetch } = useApi({
        url: "process-flows",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const { refetch: deleteProcessFlow } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deleteProcessFlow({ url: 'process-flows/' + id });
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
                        setCurrentProcessFlow(record)
                        setIsModalOpen(true);
                    }}>
                    </Button>

                    <Button icon={<BiListUl />} type="link" onClick={() => {
                        navigate('/process-flows/' + record.id + '/statuses')
                    }} />
                    <Button icon={<BiSignal5 />} type="link" onClick={() => {
                        navigate('/process-flows/' + record.id + '/steps')
                    }} />
                    <Button icon={<BiCog />} type="link" onClick={() => {
                        setCurrentProcessFlow(record)
                        setIsModalConfigOpen(true)
                    }} />
                    <Popconfirm
                        title={`Bạn có chắc muốn xoá ProcessFlow "${record.name}"?`}
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
            params: { page: newPagination.page, size: newPagination.size, search: searchText },
        });
    };

    useEffect(() => {
        refetch({
            params: { page: pagination.page, size: pagination.size, search: searchText },
        });
    }, [searchText]);

    if (error) {
        return <p style={{ color: "red" }}>Error: {error.message}</p>;
    }

    return (
        <div style={{ padding: 16 }}>
            <h1 style={{ fontSize: 20, fontWeight: "bold", marginBottom: 16 }}>
                Process Flow Management
            </h1>
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => {
                        setCurrentProcessFlow(null)
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

            <CreateProcessFlow refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentProcessFlow} />
            <ProcessFlowEditor onCancel={() => setIsModalConfigOpen(false)} open={isModalConfigOpen} processFlowId={currentProcessFlow?.id} />

        </div>
    );
}
