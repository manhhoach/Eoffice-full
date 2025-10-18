import { useEffect } from "react";
import { Modal, Form, Input, message, Checkbox, InputNumber } from 'antd';
import useApi from "../../hooks/useApi";
import { Select } from "antd";
import Select2 from "../../components/Select2";
const { Option } = Select;


export default function CreateStep({ open, onCancel, initialData, refetch, processFlowId }) {
   const [form] = Form.useForm();

   const { refetch: createStep } = useApi({
      url: 'process-steps',
      method: 'POST',
      auto: false,
   });
   const { refetch: editStep } = useApi({
      url: 'process-steps/' + initialData?.id,
      method: 'PUT',
      auto: false,
   });


   const { data: config } = useApi({
      url: 'process-steps/config/' + processFlowId,
   })

   useEffect(() => {
      if (initialData) {
         form.setFieldsValue({
            name: initialData.name,
            isReturn: initialData.isReturn,
            needToNote: initialData.needToNote,
            needToFile: initialData.needToFile,
            isSameDepartment: initialData.isSameDepartment,
            returnType: initialData.returnType,
            receptionRoles: initialData.receptionRoles,
            startProcessStatusId: initialData.startProcessStatusId,
            endProcessStatusId: initialData.endProcessStatusId,
         });
      } else {
         form.resetFields();
      }
   }, [initialData, form]);

   const handleFinish = async (values) => {
      const formData = {
         name: values.name,
         isReturn: values.isReturn,
         needToNote: values.needToNote,
         needToFile: values.needToFile,
         isSameDepartment: values.isSameDepartment,
         returnType: values.returnType,
         receptionRoles: values.receptionRoles,
         startProcessStatusId: values.startProcessStatusId,
         endProcessStatusId: values.endProcessStatusId,
         processFlowId
      };
      try {
         initialData != null ? await editStep({ body: formData }) : await createStep({ body: formData });
         message.success("Step update successfully!");
         form.resetFields();
         onCancel();
         await refetch();
      } catch (err) {
         message.error("Failed to update tour: " + (err.message || 'Unknown error'));
      }
   };


   return (
      <Modal
         title="Create/Edit Step"
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
               rules={[{ required: true, message: "Please input Step name!" }]}
            >
               <Input />
            </Form.Item>


            <Form.Item label="Start Process Status" name="startProcessStatusId">
               <Select2 displayKey={'name'} valueKey={'id'} data={config?.data.statuses} ></Select2>
            </Form.Item>

            <Form.Item label="End Process Status" name="endProcessStatusId">
               <Select2 displayKey={'name'} valueKey={'id'} data={config?.data.statuses} ></Select2>
            </Form.Item>


            <Form.Item label="Reception Roles" name="receptionRoles">
               <Select2 displayKey={'name'} valueKey={'id'} data={config?.data.roles} multiple={true}></Select2>
            </Form.Item>

            <Form.Item
               valuePropName="checked"
               label="Is Return"
               name="isReturn"
            >
               <Checkbox />
            </Form.Item>

            <Form.Item
               valuePropName="checked"
               label="Need To Note"
               name="needToNote"
            >
               <Checkbox />
            </Form.Item>

            <Form.Item
               valuePropName="checked"
               label="Required File"
               name="requiredFile"
            >
               <Checkbox />
            </Form.Item>

            <Form.Item
               valuePropName="checked"
               label="Same department"
               name="isSameDepartment"
            >
               <Checkbox />
            </Form.Item>


         </Form>
      </Modal>
   )
}