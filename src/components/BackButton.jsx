import { useNavigate } from "react-router-dom";
import { BiReply } from "react-icons/bi";
import { Button } from "antd";

export default function BackButton() {
   const navigate = useNavigate()
   return (
      <Button icon={<BiReply />} onClick={() => navigate(-1)}></Button>
   )
}