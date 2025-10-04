import { useEffect, useState } from "react";
import { Table, Button, Space, Input, Popconfirm } from "antd";
import useApi from "../hooks/useApi";
import CreateModule from "../partials/module/CreateModule";
import { BiCheck, BiCog, BiEdit, BiPlus, BiTrash, BiX } from "react-icons/bi";
import DEFAULT_PAGINATION from "../constants/pagination";
import { useNavigate } from "react-router-dom";

const { Search } = Input;

export default function ModuleManagement() {
    const [pagination, setPagination] = useState(DEFAULT_PAGINATION);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [searchText, setSearchText] = useState("");
    const [currentModule, setCurrentModule] = useState(null);
    const navigate = useNavigate();

    const { data, loading, error, refetch } = useApi({
        url: "modules/paged",
        params: { page: pagination.current, size: pagination.pageSize, search: searchText },
    });

    const { refetch: deleteModule } = useApi({
        method: 'DELETE',
        auto: false,
    });

    const handleDelete = async (id) => {
        await deleteModule({ url: 'modules/' + id });
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
                        setCurrentModule(record)
                        setIsModalOpen(true);
                    }}>
                    </Button>

                    <Button icon={<BiCog />} type="link" onClick={() => {
                        navigate('/modules/' + record.id + '/permissions')
                    }}>

                    </Button>

                    <Popconfirm
                        title={`Bạn có chắc muốn xoá Module "${record.name}"?`}
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
                Module Management
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

            <CreateModule refetch={refetch} onCancel={() => setIsModalOpen(false)} open={isModalOpen} initialData={currentModule} />
        </div>
    );
}
