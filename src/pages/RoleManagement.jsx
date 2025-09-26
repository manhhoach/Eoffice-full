import React, { useState } from "react";
import { Table, Button, Space, Modal, Form } from "antd";
import useApi from "../hooks/useApi";
import CreateRole from "../partials/role/CreateRole";

export default function RoleManagement() {
    const [pagination, setPagination] = useState({ current: 1, pageSize: 10 });
    const [isModalOpen, setIsModalOpen] = useState(false);

    const { data, loading, error, refetch } = useApi({
        url: "roles/paged",
        params: { page: pagination.current, size: pagination.pageSize },
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
                    <Button type="link" onClick={() => console.log("Edit", record)}>
                        Edit
                    </Button>
                    <Button
                        type="link"
                        danger
                        onClick={() => console.log("Delete", record)}
                    >
                        Delete
                    </Button>
                </Space>
            ),
        },
    ];

    const handleTableChange = (newPagination) => {
        setPagination(newPagination);
        refetch({
            params: { page: newPagination.current, size: newPagination.pageSize },
        });
    };

    if (error) {
        return <p style={{ color: "red" }}>Error: {error.message}</p>;
    }

    return (
        <div style={{ padding: 16 }}>
            <h1 style={{ fontSize: 20, fontWeight: "bold", marginBottom: 16 }}>
                Role Management
            </h1>
            <Button
                type="primary"
                onClick={() => setIsModalOpen(true)}
                style={{ marginBottom: 16 }}
            >
                + Create Role
            </Button>
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
