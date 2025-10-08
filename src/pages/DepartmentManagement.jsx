import { useEffect, useState } from "react";
import { Table, Button, Space, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi.js";
import CreateDepartment from "../partials/department/CreateDepartment.jsx";
import { BiEdit, BiPlus, BiTrash } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination.js";

const { Search } = Input;

export default function DepartmentManagement() {
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchText, setSearchText] = useState("");
    const [currentDepartment, setCurrentDepartment] = useState(null);

    const { data, loading, error, refetch } = useApi({
        url: "departments/paged",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const { refetch: deleteDepartment } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deleteDepartment({ url: 'departments/' + id });
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
                        setCurrentDepartment(record)
                        setIsModalOpen(true);
                    }}>
                    </Button>

                    <Popconfirm
                        title={`Bạn có chắc muốn xoá Department "${record.name}"?`}
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
                Department Management
            </h1>
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => {
                        setCurrentDepartment(null)
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

            <CreateDepartment refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentDepartment} />
        </div>
    );
}
