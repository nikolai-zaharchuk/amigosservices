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
import CreateCustomerForm from "./CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const CreateCustomerDrawer = ({ fetchCustomers }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();

    return (
        <>
            {/* Кнопка для открытия Drawer */}
            <Button leftIcon={<ChakraAddIcon />} colorScheme="teal" onClick={onOpen}>
                Create customer
            </Button>

            {/* Drawer */}
            <Drawer isOpen={isOpen} onClose={onClose} size="xl">
                <DrawerOverlay />

                {/* Используем DrawerContent напрямую, без оберток */}
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Create new customer</DrawerHeader>

                    <DrawerBody>
                        <CreateCustomerForm onSuccess={fetchCustomers} />
                    </DrawerBody>

                    <DrawerFooter>
                        <Button leftIcon={<ChakraCloseIcon />} colorScheme="teal" onClick={onClose}>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
};

export default CreateCustomerDrawer;