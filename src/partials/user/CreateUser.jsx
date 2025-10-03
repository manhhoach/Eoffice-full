import { useEffect } from "react";
import { Modal, Form, Input, message } from 'antd';
import useApi from "../../hooks/useApi";


export default function CreateUser({ open, onCancel, initialData, refetch }) {
    const [form] = Form.useForm();

    const { refetch: createUser } = useApi({
        url: 'users',
        method: 'POST',
        auto: false,
    });

    const { refetch: editUser } = useApi({
        url: 'users/' + initialData?.id,
        method: 'PUT',
        auto: false,
    });

    useEffect(() => {
        if (initialData) {
            form.setFieldsValue({
                username: initialData.username,
            });
        } else {
            form.resetFields();
        }
    }, [initialData, form]);

    const handleFinish = async (values) => {
        const formData = {
            username: values.username,
        };

        try {
            initialData != null ? await editUser({ body: formData }) : await createUser({ body: formData });
            form.resetFields();
            onCancel();
            await refetch();
        } catch (err) {
            message.error("Failed to update tour: " + (err.message || 'Unknown error'));
        }
    };


    return (
        <Modal
            title="Create/Edit User"
            destroyOnHidden
            open={open}
            onCancel={() => {
                onCancel();
                form.resetFields();
            }}
            onOk={() => {
                form.submit()
            }}
        >
            <Form form={form} onFinish={handleFinish} layout="vertical" >
                <Form.Item
                    label="Username"
                    name="username"
                    rules={[{ required: true, message: "Please input role name!" }]}
                >
                    <Input />
                </Form.Item>

            </Form>
        </Modal>
    )
}