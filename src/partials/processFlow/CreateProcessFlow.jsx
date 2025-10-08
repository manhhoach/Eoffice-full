import { useEffect } from "react";
import { Modal, Form, Input, message, Checkbox } from 'antd';
import useApi from "../../hooks/useApi";


export default function CreateProcessFlow({ open, onCancel, initialData, refetch }) {
   const [form] = Form.useForm();

   const { refetch: createProcessFlow } = useApi({
      url: 'process-flows',
      method: 'POST',
      auto: false,
   });
   const { refetch: editProcessFlow } = useApi({
      url: 'process-flows/' + initialData?.id,
      method: 'PUT',
      auto: false,
   });

   useEffect(() => {
      if (initialData) {
         form.setFieldsValue({
            name: initialData.name
         });
      } else {
         form.resetFields();
      }
   }, [initialData, form]);

   const handleFinish = async (values) => {
      const formData = {
         name: values.name
      };

      try {
         initialData != null ? await editProcessFlow({ body: formData }) : await createProcessFlow({ body: formData });
         message.success("Process Flow update successfully!");
         form.resetFields();
         onCancel();
         await refetch();
      } catch (err) {
         message.error("Failed to update tour: " + (err.message || 'Unknown error'));
      }
   };


   return (
      <Modal
         title="Create/Edit Process Flow"
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
               rules={[{ required: true, message: "Please input name!" }]}
            >
               <Input />
            </Form.Item>

         </Form>
      </Modal>
   )
}