import { useEffect } from "react";
import { Modal, Form, Input, message, Checkbox } from 'antd';
import { generateCodeFromName } from '../../utils/Utils';
import useApi from "../../hooks/useApi";


export default function CreateModule({ open, onCancel, initialData, refetch }) {
    const [form] = Form.useForm();

    const { refetch: createModule } = useApi({
        url: 'modules',
        method: 'POST',
        auto: false,
    });
    const { refetch: editModule } = useApi({
        url: 'modules/' + initialData?.id,
        method: 'PUT',
        auto: false,
    });

    useEffect(() => {
        if (initialData) {
            form.setFieldsValue({
                name: initialData.name,
                code: initialData.code,
                isDisplayed: initialData.isDisplayed
            });
        } else {
            form.resetFields();
        }
    }, [initialData, form]);

    const handleFinish = async (values) => {
        const formData = {
            name: values.name,
            code: values.code,
            isDisplayed: values.isDisplayed
        };

        try {
            initialData != null ? await editModule({ body: formData }) : await createModule({ body: formData });
            message.success("Module update successfully!");
            form.resetFields();
            onCancel();
            await refetch();
        } catch (err) {
            message.error("Failed to update tour: " + (err.message || 'Unknown error'));
        }
    };


    return (
        <Modal
            title="Create/Edit Module"
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
                    rules={[{ required: true, message: "Please input Module name!" }]}
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