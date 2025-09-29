import { Form } from "antd";
import useApi from "../../hooks/useApi";


export default function SetPermission({ open, onCancel, initialData, roleId }) {


   // lấy ra list permission group by module

   // đổ ra list checkbox
   // mỗi checkbox là 1 permission
   // checkbox group là module

   const [form] = Form.useForm();

   const { refetch: createRole } = useApi({
      url: 'roles',
      method: 'POST',
      auto: false,
   });

   const { refetch: editRole } = useApi({
      url: 'roles/' + initialData?.id,
      method: 'PUT',
      auto: false,
   });

   useEffect(() => {
      if (initialData) {
         form.setFieldsValue({
            name: initialData.name,
            code: initialData.code,
         });
      } else {
         form.resetFields();
      }
   }, [initialData, form]);

   const handleFinish = async (values) => {
      const formData = {
         name: values.name,
         code: values.code,
      };

      try {
         initialData != null ? await editRole({ body: formData }) : await createRole({ body: formData });
         message.success("Role update successfully!");
         form.resetFields();
         onCancel();
         await refetch();
      } catch (err) {
         message.error("Failed to update tour: " + (err.message || 'Unknown error'));
      }
   };


   return (
      <Modal
         title="Create/Edit Role"
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
               rules={[{ required: true, message: "Please input role name!" }]}
            >
               <Input />
            </Form.Item>
            <Form.Item
               label="Code"
               name="code"
            >
               <Input />
            </Form.Item>

         </Form>
      </Modal>
   )
}