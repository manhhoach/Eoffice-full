import React, { useContext, useState } from "react";
import { Form, Input, Button, Card, Typography } from "antd";
import useApi from "../hooks/useApi";
import { MainContext } from "../contexts/MainContext";
import { useNavigate } from "react-router-dom";

const { Title } = Typography;

function Login() {
  const { data, loading, error, refetch } = useApi({
    url: 'auth/login',
    method: 'POST',
    auto: false,
  })
  const navigate = useNavigate();
  const { setUser, setPermissionCodes, setModules } = useContext(MainContext);

  const onFinish = async (values) => {
    try {
      const res = await refetch({ body: values });
      if (res.success) {
        localStorage.setItem("accessToken", res.data.accessToken);
        setPermissionCodes(res.data.permissionCodes)
        setUser(res.data.username);
        setModules(res.data.modules);
        navigate("/");
      }
      else {
        alert(res.message)
      }
    } catch (err) {
      console.error("Login failed:", err);
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "#f9fafb",
      }}
    >
      <Card style={{ width: 400, borderRadius: 12, boxShadow: "0 2px 8px rgba(0,0,0,0.1)" }}>
        <Title level={3} style={{ textAlign: "center", color: "#722ed1" }}>
          Login
        </Title>
        <Form layout="vertical" onFinish={onFinish}>
          <Form.Item
            label="Username"
            name="username"
            rules={[{ required: true, message: "Please enter your username" }]}
          >
            <Input placeholder="Enter your username" />
          </Form.Item>

          <Form.Item
            label="Password"
            name="password"
            rules={[{ required: true, message: "Please enter your password" }]}
          >
            <Input.Password placeholder="Enter your password" />
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              loading={loading}
              block
              style={{ borderRadius: 6 }}
            >
              Login
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
}

export default Login;
