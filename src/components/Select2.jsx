import { Select } from "antd";

const { Option } = Select;

export default function Select2({ data = [], value, onChange, valueKey, displayKey, placeholder, multiple = false }) {
   return (
      <Select
         style={{ width: 200 }}
         placeholder={placeholder || "Select values"}
         value={value}
         onChange={onChange}
         showSearch
         options={data.map((item) => ({
            label: item[displayKey],
            value: item[valueKey],
         }))}
         mode={multiple ? "multiple" : undefined}
      >
         {/* {data.map((item) => (
            <Option key={item[valueKey]} value={item[valueKey]}>
               {item[displayKey]}
            </Option>
         ))} */}
      </Select>
   );
}
