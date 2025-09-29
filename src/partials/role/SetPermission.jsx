import { Form, Modal } from "antd";
import useApi from "../../hooks/useApi";


export default function SetPermission({ open, onCancel, roleId }) {


   // lấy ra list permission group by module
   const { data: modules, loading, error, refetch } = useApi({
      url: "modules?roleId=" + roleId,
   });
   console.log(modules)
   // đổ ra list checkbox
   // mỗi checkbox là 1 permission
   // checkbox group là module

   const [form] = Form.useForm();



   return (
      <Modal
         title="Set permissions"
         destroyOnHidden
         open={open}
         maskClosable={false}
         onCancel={() => {
            onCancel();
         }}
         onOk={() => {

         }}
      >
      </Modal>
   )
}