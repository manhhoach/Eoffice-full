import { Checkbox, Form, Modal, Select } from "antd";
import useApi from "../../hooks/useApi";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
const { Option } = Select;

export default function SetRole({ open, onCancel, userId }) {
   const [form] = Form.useForm();
   const { data } = useApi({
      url: "roles/get-current-roles?userId=" + userId,
   });
   const { refetch: setRoles } = useApi({
      method: 'POST',
      url: "roles/assign-roles",
      auto: false,
   })
   const departments = data?.data || [];

   const [selectedDeptId, setSelectedDeptId] = useState();
   const [departmentsState, setDepartmentsState] = useState([]);
   const selectedDept = departmentsState.find(
      (d) => d.departmentId === selectedDeptId
   );

   useEffect(() => {
      if (departments.length) {
         setDepartmentsState(departments);
         setSelectedDeptId(departments[0].departmentId);
      }
   }, [departments]);

   const handleRoleChange = (deptId, roleId, checked) => {
      setDepartmentsState((prev) =>
         prev.map((dept) =>
            dept.departmentId === deptId
               ? {
                  ...dept,
                  userRoleStatuses: dept.userRoleStatuses.map((role) =>
                     role.id === roleId ? { ...role, selected: checked } : role
                  ),
               }
               : dept
         )
      );
   };


   const handleSubmit = async () => {
      try {
         let assignments = [];
         departmentsState.forEach((dept) => {
            if (dept.userRoleStatuses && dept.userRoleStatuses.length > 0) {
               dept.userRoleStatuses.forEach((role) => {
                  if (role.selected) {
                     assignments.push({
                        departmentId: dept.departmentId,
                        roleId: role.id
                     })
                  }
               })
            }
         });
         const body = {
            userId: userId,
            assignments: assignments
         }
         await setRoles({ body });
         onCancel()
         toast.success("Successfully");
      }
      catch (err) {
         toast.error(err.message);
      }
   }

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
         <Form form={form} onFinish={handleSubmit} layout="vertical">
            <Select
               style={{ width: "100%", marginBottom: 16 }}
               value={selectedDeptId}
               onChange={(value) => setSelectedDeptId(value)}
            >
               {departments.map((dept) => (
                  <Option key={dept.departmentId} value={dept.departmentId}>
                     {dept.departmentName}
                  </Option>
               ))}
            </Select>


            {selectedDept?.userRoleStatuses.map((role) => (
               <div key={role.id}>
                  <Checkbox
                     checked={role.selected}
                     onChange={(e) =>
                        handleRoleChange(selectedDept.departmentId, role.id, e.target.checked)
                     }
                  >
                     {role.name}
                  </Checkbox>
               </div>
            ))}
         </Form>
      </Modal>
   )
}
