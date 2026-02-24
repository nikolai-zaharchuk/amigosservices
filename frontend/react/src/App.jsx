import {use, useEffect, useState} from "react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {getCustomers} from "./services/client.js";
import {Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import SocialProfileWithImage from "./components/Card.jsx";
import CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";


function App() {

  const [customers, setCustomers] = useState([])
  const [loading, setLoading ] = useState(false)
  const [error, setError ] = useState("")

  const fetchCustomers = () => {
      setLoading(true);
      getCustomers().then(response => {
          setCustomers(response.data);
      }).catch(err => {
          setError(err)
      }).finally(() => {
          setLoading(false);
      });
  }

  useEffect(() => {
      fetchCustomers();
  }, []);

  if (loading) {
    return (
        <SidebarWithHeader>
          <Spinner
            thickness={"4px"}
            speed={"0.65s"}
            emptyColor={"gray.200"}
            color={"blue.500"}
            size={"XL"}
          />
        </SidebarWithHeader>
    )
  }

  if (error) {
      return (
          <SidebarWithHeader>
              <CreateCustomerDrawer
                  fetchCustomers={fetchCustomers}
              />
              <Text mt={5}>
                  Ooops there was an error
              </Text>
          </SidebarWithHeader>
      );
  }

  if (customers.length <= 0) {
    return (
        <SidebarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers={fetchCustomers}
            />
            <Text mt={5}>
                No customers
            </Text>
        </SidebarWithHeader>
    );
  }

  return (
      <SidebarWithHeader>
          <CreateCustomerDrawer
              fetchCustomers={fetchCustomers}
          />
          <Wrap justifySelf={"center"} spacing={"30px"}>
              {customers.map((customer, index) => {
                  return (
                      <WrapItem key={index}>
                          <SocialProfileWithImage
                              key={index}
                              {...customer}
                              fetchCustomers={fetchCustomers}
                          />
                      </WrapItem>
                  )
              })}
          </Wrap>
      </SidebarWithHeader>
  );
}

export default App
