import { useEffect, useState } from "react";
import { Table, Button, Space, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi";
import CreateUser from "./../partials/user/CreateUser";
import { BiColor, BiCog, BiEdit, BiPlus, BiTrash } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination";
import SetRole from "../partials/user/SetRole";
import SetPermissionForUser from "../partials/user/SetPermissionForUser";


const { Search } = Input;

export default function UserManagement() {
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [modalOpen, setIsModalOpen] = useState({
        createUser: false,
        setRole: false,
        setPermission: false
    });
    const [searchText, setSearchText] = useState("");
    const [currentUser, setCurrentUser] = useState(null);

    const { data, loading, error, refetch } = useApi({
        url: "users/paged",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const { refetch: deleteUser } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deleteUser({ url: 'users/' + id });
        refetch();
    }


    const columns = [
        {
            title: "Username",
            dataIndex: "username",
            key: "username",
        },
        {
            title: "Actions",
            key: "actions",
            render: (_, record) => (
                <Space>
                    <Button icon={<BiEdit />} type="link" onClick={() => {
                        setCurrentUser(record)
                        setIsModalOpen(modalOpen => ({ ...modalOpen, createUser: true }));
                    }}>
                    </Button>

                    <Button icon={<BiCog />} type="link" onClick={() => {
                        setCurrentUser(record)
                        setIsModalOpen(modalOpen => ({ ...modalOpen, setRole: true }));
                    }}>
                    </Button>

                    <Button icon={<BiColor />} type="link" onClick={() => {
                        setCurrentUser(record)
                        setIsModalOpen(modalOpen => ({ ...modalOpen, setPermission: true }));
                    }}></Button>

                    <Popconfirm
                        title={`Bạn có chắc muốn xoá User "${record.username}"?`}
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
                User Management
            </h1>
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => {
                        setCurrentUser(null)
                        setIsModalOpen(modalOpen => ({ ...modalOpen, createUser: true }))
                    }
                    }
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

            <CreateUser refetch={refetch} onCancel={() => setIsModalOpen(modalOpen => ({ ...modalOpen, createUser: false }))} open={modalOpen.createUser} initialData={currentUser} />
            <SetRole onCancel={() => setIsModalOpen(modalOpen => ({ ...modalOpen, setRole: false }))} open={modalOpen.setRole} userId={currentUser?.id} />
            <SetPermissionForUser onCancel={() => setIsModalOpen(modalOpen => ({ ...modalOpen, setPermission: false }))} open={modalOpen.setPermission} userId={currentUser?.id} />
        </div>
    );
}
