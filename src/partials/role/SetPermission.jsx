import { Checkbox, Col, Form, Modal, Row } from "antd";
import useApi from "../../hooks/useApi";
import { useEffect } from "react";
import { toast } from "react-toastify";


export default function SetPermission({ open, onCancel, roleId }) {
   const { data, loading, error, refetch } = useApi({
      url: `roles/${roleId}/modules`,
   });

   const { refetch: setPermissions } = useApi({
      method: 'POST',
      url: `roles/${roleId}/modules`,
      auto: false,
   })

   const modules = data?.data || [];
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
      form.setFieldsValue(
         modules?.reduce((acc, module) => {
            module.permissionSelectionDtos?.forEach(permission => {
               acc[permission.id] = permission.selected;
            });
            return acc;
         }, {})
      );
   }, [roleId, modules]);

   return (
      <Modal
         title="Set permissions"
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
            initialValues={
               modules?.reduce((acc, module) => {
                  module.permissionSelectionDtos?.forEach(permission => {
                     acc[permission.id] = permission.selected; 
                  });
                  return acc;
               }, {})
            }
         >
            <Row>
               {modules?.map(module => (
                  <Col span={12} key={module.name}>
                     <div>
                        <span>{module.name}</span>
                        {module.permissionSelectionDtos?.map(permission => (
                           <Form.Item
                              key={permission.id}
                              name={permission.id}
                              valuePropName="checked"
                           >
                              <Checkbox>{permission.name}</Checkbox>
                           </Form.Item>
                        ))}
                     </div>
                  </Col>
               ))}
            </Row>
         </Form>
      </Modal>
   )
}
