import axios from "axios";

export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/customers`);
    } catch (e) {
        throw e;
    }
}

export const customerProfilePictureUrl = (randomUserGender, customerId) => {
    return `https://randomuser.me/api/portraits/${randomUserGender}/${customerId}.jpg`
}