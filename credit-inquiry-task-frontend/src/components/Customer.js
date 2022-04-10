import { Form, Input, Button, message, InputNumber } from 'antd';
import React from 'react';
import axios from 'axios';

const Customer = () => {
  const onFinish = (values) => {
    let body = {
        "identityNumber": Number(values.identityNumber),
        "name": values.name, 
        "surName": values.surname,
        "monthlyIncome": Number(values.monthlyIncome),
        "telephone": Number(values.telephone)
      };
      console.log('Success:', body);
    

    axios.post('http://localhost:8080/credit/applyForCredit', body)
      .then(function (response) {
          if (response?.data?.errorCode !== undefined) {
              message.error(response.data.message);
          } else if (response?.data?.creditStatusEnum !== undefined) {
            if(response.data.creditStatusEnum === "Accept") {
                message.success("The credit application has been accepted. Limit:" + response.data.creditLimit);
            } else {
                message.success("The credit application not accepted");
            }
          }
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
        message.error("Request not send");
      });
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  return (
      <div>
          <h1> Credit Application</h1>
          <br/>
    <Form
      name="basic"
      labelCol={{
        span: 8,
      }}
      wrapperCol={{
        span: 16,
      }}
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item
        label="Identity Number"
        name="identityNumber"
        rules={[
          {
            required: true,
            message: 'Please input your username!',
          },
        ]}
      >
        <InputNumber style={{width:"10em"}} min={10000000000} max={99999999999}/>
      </Form.Item>

      <Form.Item
        label="Name"
        name="name"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item
        label="Surname"
        name="surname"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
      >
        <Input/>
      </Form.Item>
      <Form.Item
        label="Monthly Income"
        name="monthlyIncome"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
      >
        <InputNumber style={{width:"10em"}} min={0} max={9999999999}/>
      </Form.Item>
      <Form.Item
        label="Telephone"
        name="telephone"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
      >
         <InputNumber style={{width:"10em"}} min={1000000000} max={9999999999}/>
      </Form.Item>
      <Form.Item
        wrapperCol={{
          offset: 8,
          span: 16,
        }}
      >
        <Button type="primary" htmlType="submit">
          Get Credit Status
        </Button>
      </Form.Item>
    </Form>
    </div>
  );
};

export default Customer;