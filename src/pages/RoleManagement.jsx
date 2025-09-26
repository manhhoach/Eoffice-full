import React from "react";
import { Table } from "antd";
import useApi from "../hooks/useApi";

export default function RoleManagement() {
    const { data, loading, error } = useApi({ url: "roles" });

    const columns = [
        {
            title: "ID",
            dataIndex: "id",
            key: "id",
        },
        {
            title: "Name",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Description",
            dataIndex: "description",
            key: "description",
        },
    ];

    if (error) {
        return <p style={{ color: "red" }}>Error: {error.message}</p>;
    }

    return (
        <div style={{ padding: 16 }}>
            <h1 style={{ fontSize: 20, fontWeight: "bold", marginBottom: 16 }}>
                Role Management
            </h1>
            <Table
                rowKey="id"
                columns={columns}
                dataSource={data || []}
                loading={loading}
                bordered
            />
        </div>
    );
}
