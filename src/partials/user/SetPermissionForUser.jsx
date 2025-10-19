import { Checkbox, Col, Form, Modal, Row } from "antd";
import useApi from "../../hooks/useApi";
import { useEffect } from "react";
import { toast } from "react-toastify";


export default function SetPermissionForUser({ onCancel, userId }) {
   const { data } = useApi({
      url: `permissions/users/${userId}`,
   });

   const { refetch: setPermission } = useApi({
      method: 'PUT',
      url: `permissions/users/${userId}`,
      auto: false,
   })

   const permissions = data?.data || [];

   const [form] = Form.useForm();

   const handleSubmit = async (values) => {
      try {
         const permissionIds = Object.keys(values).filter(key => values[key]).map(id => parseInt(id));
         const body = {
            permissionIds: permissionIds
         }
         await setPermission({ body });
         onCancel()
         toast.success("Successfully");
      }
      catch (error) {
         toast.error("Failed to set permissions");
      }
   }
   useEffect(() => {
      form.resetFields();
      form.setFieldsValue(
         permissions?.reduce(
            (acc, permission) => {
               acc[permission.id] = permission.selected;
               return acc;
            }, {}
         )
      )

   }, [userId, permissions]);

   return (
      <Modal
         title="Set Permission"
         destroyOnHidden
         open={true}
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
            initialValues={permissions?.reduce(
               (acc, permission) => {
                  acc[permission.id] = permission.selected;
                  return acc;
               }, {}
            )}
         >
            <Row>
               {permissions?.map(permission => (
                  <Col span={12} key={permission.id}>
                     <Form.Item
                        key={permission.id}
                        name={permission.id}
                        valuePropName="checked"
                     >
                        <Checkbox>{permission.name}</Checkbox>
                     </Form.Item>
                  </Col>
               ))}
            </Row>
         </Form>
      </Modal>
   )
}
