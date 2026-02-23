import {use, useEffect, useState} from "react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {getCustomers} from "./services/client.js";
import {Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import SocialProfileWithImage from "./components/Card.jsx";


function App() {

  const [customers, setCustomers] = useState([])
  const [loading, setLoading ] = useState(false)

  useEffect(() => {
    setLoading(true);
    getCustomers().then(response => {
      setCustomers(response.data);
    }).catch(err => {
      console.log(err)
    }).finally(() => {
      setLoading(false);
    });
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

  if (customers.length <= 0) {
    return (
        <SidebarWithHeader>
          <Text>
            No customers
          </Text>
        </SidebarWithHeader>
    );
  }

  {console.log(customers)}
  {console.log(customers.length)}

  return (
      <SidebarWithHeader>
          <Wrap justifySelf={"center"} spacing={"30px"}>
              {customers.map((customer, index) => {
                  return (
                      <WrapItem key={index}>
                          <SocialProfileWithImage
                              key={index}
                              {...customer}
                          />
                      </WrapItem>
                  )
              })}
          </Wrap>
      </SidebarWithHeader>
  );
}

export default App
