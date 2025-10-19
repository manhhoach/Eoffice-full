import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Table, Button, Space, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi";
import CreatePermission from "../partials/permission/CreatePermission";
import { BiCheck, BiEdit, BiPlus, BiTrash, BiX } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination";
import BackButton from './../components/BackButton'
const { Search } = Input;

export default function PermissionManagement() {
    const { id } = useParams();
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchText, setSearchText] = useState("");
    const [currentPermission, setCurrentPermission] = useState(null);


    const { data, loading, error, refetch } = useApi({
        url: "permissions",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText, moduleId: id },
    });

    const { refetch: deletePermission } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deletePermission({ url: 'permissions/' + id });
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
            title: "Url",
            dataIndex: "url",
            key: "url",
        },
        {
            title: "Displayed",
            dataIndex: "isDisplayed",
            key: "isDisplayed",
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
                        setCurrentPermission(record)
                        setIsModalOpen(true);
                    }}>
                    </Button>

                    <Popconfirm
                        title={`Bạn có chắc muốn xoá Permission "${record.name}"?`}
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
                Permission Management
            </h1>
            <BackButton />
            <div style={{ gap: 16, display: "flex", justifyContent: "space-between" }}>
                <Search onChange={(e) => { setSearchText(e.target.value) }}></Search>
                <Button
                    type="primary"
                    onClick={() => {
                        setCurrentPermission(null)
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

            <CreatePermission moduleId={id} refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentPermission} />
        </div>
    );
}
