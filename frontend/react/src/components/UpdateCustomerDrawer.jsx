import React from "react";
import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import { AddIcon as ChakraAddIcon, CloseIcon as ChakraCloseIcon } from "@chakra-ui/icons";
import UpdateCustomerForm from "./UpdateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const UpdateCustomerDrawer = ({ initialValues, customerId, fetchCustomers }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();

    return (
        <>
            {/* Кнопка для открытия Drawer */}
            <Button colorScheme="teal" onClick={onOpen}>
                Update customer
            </Button>

            {/* Drawer */}
            <Drawer isOpen={isOpen} onClose={onClose} size="xl">
                <DrawerOverlay />

                {/* Используем DrawerContent напрямую, без оберток */}
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Update customer id {customerId}</DrawerHeader>

                    <DrawerBody>
                        <UpdateCustomerForm
                            initialValues = {initialValues}
                            customerId = {customerId}
                            onSuccess = {fetchCustomers}
                        />
                    </DrawerBody>

                    <DrawerFooter>
                        <Button leftIcon = {<ChakraCloseIcon />} colorScheme="teal" onClick={onClose}>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
};

export default UpdateCustomerDrawer;
