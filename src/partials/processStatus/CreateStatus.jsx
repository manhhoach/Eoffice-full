import { useEffect } from "react";
import { Modal, Form, Input, message, Checkbox, InputNumber } from 'antd';
import { generateCodeFromName } from '../../utils/Utils';
import useApi from "../../hooks/useApi";


export default function CreateStatus({ open, onCancel, initialData, refetch, processFlowId }) {
   const [form] = Form.useForm();

   const { refetch: createStatus } = useApi({
      url: 'process-statuses',
      method: 'POST',
      auto: false,
   });
   const { refetch: editStatus } = useApi({
      url: 'process-statuses/' + initialData?.id,
      method: 'PUT',
      auto: false,
   });

   useEffect(() => {
      if (initialData) {
         form.setFieldsValue({
            name: initialData.name,
            isStart: initialData.isStart,
            isEnd: initialData.isEnd
         });
      } else {
         form.resetFields();
      }
   }, [initialData, form]);

   const handleFinish = async (values) => {
      const formData = {
         name: values.name,
         isStart: values.isStart,
         isEnd: values.isEnd,
         processFlowId: processFlowId
      };

      try {
         initialData != null ? await editStatus({ body: formData }) : await createStatus({ body: formData });
         message.success("Status update successfully!");
         form.resetFields();
         onCancel();
         await refetch();
      } catch (err) {
         message.error("Failed to update tour: " + (err.message || 'Unknown error'));
      }
   };


   return (
      <Modal
         title="Create/Edit Status"
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
         <Form form={form} onFinish={handleFinish} layout="vertical">
            <Form.Item
               label="Name"
               name="name"
               rules={[{ required: true, message: "Please input Status name!" }]}
            >
               <Input />
            </Form.Item>
            <Form.Item
               valuePropName="checked"
               label="Is Start"
               name="isStart"
            >
               <Checkbox />
            </Form.Item>
            <Form.Item
               valuePropName="checked"
               label="Is End"
               name="isEnd"
            >
               <Checkbox />
            </Form.Item>
         </Form>
      </Modal>
   )
}