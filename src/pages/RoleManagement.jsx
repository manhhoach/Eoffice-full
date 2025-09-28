import React, { useEffect, useState } from "react";
import { Table, Button, Space, Modal, Form, Input } from "antd";
import useApi from "../hooks/useApi";
import CreateRole from "../partials/role/CreateRole";
import { BiEdit, BiPlus, BiTrash } from "react-icons/bi";

const { Search } = Input;

export default function RoleManagement() {
    const [pagination, setPagination] = useState({ page: 1, size: 10 });
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchText, setSearchText] = useState("");

    const { data, loading, error, refetch } = useApi({
        url: "roles/paged",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const columns = [
        {
            title: "Code",
            dataIndex: "code",
            key: "code",
        },
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
                    <Button icon={<BiEdit />} type="link" onClick={() => console.log("Edit", record)}>

                    </Button>
                    <Button
                        type="link"
                        icon={<BiTrash />}
                        danger
                        onClick={() => console.log("Delete", record)}
                    >

                    </Button>
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
                Role Management
            </h1>
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => setIsModalOpen(true)}
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

            <CreateRole initialData={null}></CreateRole>
        </div>
    );
}
