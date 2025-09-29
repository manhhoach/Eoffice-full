import React, { useEffect, useState } from "react";
import { Table, Button, Space, Modal, Form, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi";
import CreateRole from "../partials/role/CreateRole";
import { BiCog, BiEdit, BiPlus, BiTrash } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination";
import SetPermission from "../partials/role/SetPermission";

const { Search } = Input;

export default function RoleManagement() {
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [modalOpen, setIsModalOpen] = useState({
        createRole: false,
        setPermission: false
    });
    const [searchText, setSearchText] = useState("");
    const [currentRole, setCurrentRole] = useState(null);

    const { data, loading, error, refetch } = useApi({
        url: "roles/paged",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const { refetch: deleteRole } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deleteRole({ url: 'roles/' + id });
        refetch();
    }


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
                    <Button icon={<BiEdit />} type="link" onClick={() => {
                        setCurrentRole(record);
                        setIsModalOpen(modalOpen => ({ ...modalOpen, createRole: true }));
                    }}
                    ></Button>


                    <Button icon={<BiCog />} type="link" onClick={() => {
                        setCurrentRole(record);
                        setIsModalOpen(modalOpen => ({ ...modalOpen, setPermission: true }));
                    }}>

                    </Button>

                    <Popconfirm
                        title={`Bạn có chắc muốn xoá role "${record.name}"?`}
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
                Role Management
            </h1>
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => setIsModalOpen(modalOpen => ({ ...modalOpen, createRole: true }))}
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

            <CreateRole refetch={refetch} onCancel={() => setIsModalOpen(modalOpen => ({ ...modalOpen, createRole: false }))} open={modalOpen.createRole} initialData={currentRole} />

            <SetPermission onCancel={() => setIsModalOpen(modalOpen => ({ ...modalOpen, setPermission: false }))} open={modalOpen.setPermission} roleId={currentRole?.id} />
        </div>
    );
}
