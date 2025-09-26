import { useEffect } from "react";
import { Modal, Form, Input } from 'antd';


export default function CreateRole({ open, onCancel, initialData, refetch }) {
    const [form] = Form.useForm();
    useEffect(() => {
        if (initialData) {
            form.setFieldsValue({
                name: initialData.name,
                description: initialData.description,

            });
        } else {
            form.resetFields();
        }
    }, [initialData, form]);
    
    return (
        <Modal
            title="Create Role"
            open={open}
            onCancel={onCancel}
            onOk={() => {
                form.validateFields().then((values) => {
                    console.log("Create role:", values);
                    setIsModalOpen(false);
                    form.resetFields();
                    // gọi API tạo role ở đây
                });
            }}
        >
            <Form form={form} layout="vertical">
                <Form.Item
                    label="Code"
                    name="code"
                    rules={[{ required: true, message: "Please input role code!" }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[{ required: true, message: "Please input role name!" }]}
                >
                    <Input />
                </Form.Item>
            </Form>
        </Modal>
    )
}