import { Checkbox, Col, Form, Input, message, Modal, Row } from "antd";
import useApi from "../../hooks/useApi";
import { useEffect } from "react";
import { toast } from "react-toastify";


export default function SetRole({ open, onCancel, userId }) {
   const { data, loading, error, refetch } = useApi({
      url: "users/selected?userId=" + userId,
   });

   const { refetch: setPermissions } = useApi({
      method: 'POST',
      url: "users/selected",
      auto: false,
   })

   const roles = data?.data || [];
   const [form] = Form.useForm();

   const handleSubmit = async (values) => {
      const permissionIds = Object.keys(values).filter(key => values[key]).map(id => parseInt(id));
      const body = {
         roleId: roleId,
         permissionIds: permissionIds
      }
      await setPermissions({ body });
      onCancel()
      toast.success("Successfully");
   }
   useEffect(() => {
      form.resetFields();

   }, [roleId, modules]);

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
