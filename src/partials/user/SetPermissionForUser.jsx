import { Checkbox, Col, Form, Modal, Row } from "antd";
import useApi from "../../hooks/useApi";
import { useEffect } from "react";
import { toast } from "react-toastify";


export default function SetPermissionForUser({ open, onCancel, userId }) {
   const { data } = useApi({
      url: "users/selected?userId=" + userId,
   });

   const { refetch: setRoles } = useApi({
      method: 'POST',
      url: "users/selected",
      auto: false,
   })

   const roles = data?.data || [];

   const [form] = Form.useForm();

   const handleSubmit = async (values) => {
      const roleIds = Object.keys(values).filter(key => values[key]).map(id => parseInt(id));
      const body = {
         userId: userId,
         roleIds: roleIds
      }
      await setRoles({ body });
      onCancel()
      toast.success("Successfully");
   }
   useEffect(() => {
      form.resetFields();
      form.setFieldsValue(
         roles?.reduce(
            (acc, role) => {
               acc[role.id] = role.selected;
               return acc;
            }, {}
         )
      )

   }, [userId, roles]);

   return (
      <Modal
         title="Set roles"
         destroyOnHidden
         open={open}
         maskClosable={false}
         width={1000}
         onCancel={() => {
            onCancel();
         }}
         onOk={() => {
            form.submit();
         }}
      >
         <Form
            form={form}
            onFinish={handleSubmit}
            layout="vertical"
            initialValues={roles?.reduce(
               (acc, role) => {
                  acc[role.id] = role.selected;
                  return acc;
               }, {}
            )}
         >
            <Row>
               {roles?.map(role => (
                  <Col span={12} key={role.id}>
                     <Form.Item
                        key={role.id}
                        name={role.id}
                        valuePropName="checked"
                     >
                        <Checkbox>{role.name}</Checkbox>
                     </Form.Item>
                  </Col>
               ))}
            </Row>
         </Form>
      </Modal>
   )
}
