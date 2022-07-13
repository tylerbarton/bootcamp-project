// Api Information
let submitted = false;
const apiUrl = "http://localhost:8080/api/v1/user";

// Authentication
const auth_user = 'user';
const auth_pass = '11bd0a707e58-44ac85e717ec0aa5cb96';
const auth_token = 'Basic ' + btoa(auth_user + ':' + auth_pass);

/**
 * send a POST request with data as the body to the API.
 * @param {string} url  - The URL to fetch.
 * @param {string} data - The json information
 * @returns
 */
const postData = async (url = '', data = {}) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': auth_token
        },
        mode: 'cors',
        body: JSON.stringify(data)
    };

    const response = await fetch(url, fetchOptions);
    return response.status;
}

/**
 * Handles the onClick event of the confirm button to send a POST request to the API.
 */
const onConfirm = (event) => {
    // Hijack the button
    event.preventDefault();

    // Get the data from the form
    let userName = document.querySelector('input[name="name"]').value;
    const userAge = document.querySelector('input[name="age"]').value;
    const userGender = document.querySelector('input[name="gender"]').value;
    const userEmail = document.querySelector('input[name="email"]').value;
    const userPhone = document.querySelector('input[name="phone"]').value;
    userName = userName.split(" ", 2)
    const userFirstName = userName[0];
    const userLastName = userName[1];

    const email = {
        address: userEmail.toString(),
        type: "home"
    }

    const phone = {
        number: userPhone.toString(),
        type: "home"
    }

    const user = {
        // For debugging purposes, this is left in for now.
        // firstName: "Tyler",
        // lastName: "Durden",
        // age: "30",
        // gender:"m",
        // emailAddress: Array(email),
        // phoneNumber: Array(phone)

        // Actual implementation
        firstName: userFirstName,
        lastName: userLastName,
        age: userAge,
        gender: userGender,
        emailAddress: Array(email),
        phoneNumber: Array(phone)
    };

    console.log(JSON.stringify(user));
    const response = postData(apiUrl, user);

    alert("Submitted.")

    // Close the window
    window.close();
}
