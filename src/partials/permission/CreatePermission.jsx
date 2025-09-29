import { useEffect } from "react";
import { Modal, Form, Input, message, Checkbox, InputNumber } from 'antd';
import { generateCodeFromName } from '../../utils/Utils';
import useApi from "../../hooks/useApi";


export default function CreatePermission({ open, onCancel, initialData, refetch, moduleId }) {
    const [form] = Form.useForm();

    const { refetch: createPermission } = useApi({
        url: 'permissions',
        method: 'POST',
        auto: false,
    });
    const { refetch: editPermission } = useApi({
        url: 'permissions/' + initialData?.id,
        method: 'PUT',
        auto: false,
    });

    useEffect(() => {
        if (initialData) {
            form.setFieldsValue({
                name: initialData.name,
                code: initialData.code,
                url: initialData.url,
                isDisplayed: initialData.isDisplayed,
                priority: initialData.priority
            });
        } else {
            form.resetFields();
        }
    }, [initialData, form]);

    const handleFinish = async (values) => {
        const formData = {
            name: values.name,
            code: values.code,
            url: values.url,
            isDisplayed: values.isDisplayed,
            priority: values.priority,
            moduleId: moduleId
        };

        try {
            initialData != null ? await editPermission({ body: formData }) : await createPermission({ body: formData });
            message.success("Permission update successfully!");
            form.resetFields();
            onCancel();
            await refetch();
        } catch (err) {
            message.error("Failed to update tour: " + (err.message || 'Unknown error'));
        }
    };


    return (
        <Modal
            title="Create/Edit Permission"
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
            <Form form={form} onFinish={handleFinish} layout="vertical" onValuesChange={(changed, all) => {
                if (changed.name) {
                    form.setFieldValue('code', generateCodeFromName(changed.name || ''));
                }
            }}>
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[{ required: true, message: "Please input Permission name!" }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Code"
                    name="code"
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Url"
                    name="url"
                >
                    <Input />

                </Form.Item>
                <Form.Item
                    label="Priority"
                    name="priority"
                >
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item
                    valuePropName="checked"
                    label="Is Displayed"
                    name="isDisplayed"
                >
                    <Checkbox />
                </Form.Item>
            </Form>
        </Modal>
    )
}