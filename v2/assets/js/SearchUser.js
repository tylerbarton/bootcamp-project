const table_div = document.querySelector('div[class="table_information"]');
const errorMessage = document.querySelector('p[class="user_error fw-bold text-danger shake animated"]');

/**
 * Displays an error message.
 * @param message
 */
const displayError = (message) => {
    const errorMessage = document.querySelector('p[class="user_error fw-bold text-danger shake animated"]');
    errorMessage.innerHTML = message;
    errorMessage.style.display = "block";
}

/**
 * Helper method to add a user to the search table.
 * @param table - The table to add the user to.
 * @param user - The user to add.
 */
const addUserToTable = (table, user) => {
    // Define the cells
    const row = table.insertRow();
    const idCell = row.insertCell(0);
    const firstNameCell = row.insertCell(1);
    const lastNameCell = row.insertCell(2);
    const genderCell = row.insertCell(3);
    const ageCell = row.insertCell(4);
    const emailCell = row.insertCell(5);
    const phoneCell = row.insertCell(6);

    // Add the data to the cells
    idCell.innerHTML = user.id;
    firstNameCell.innerHTML = user.firstName;
    lastNameCell.innerHTML = user.lastName;
    ageCell.innerHTML = user.age;
    genderCell.innerHTML = user.gender;

    // For beautification, we only display the relevant information
    if(!!user.emailAddress){
        emailCell.innerHTML = user.emailAddress[0]['address'];
    }
    if(!!user.phoneNumber){
        phoneCell.innerHTML = user.phoneNumber[0]['number'];
    }
}

/**
 * Checks if the object is an array or not
 * @param what - The object to check
 * @returns {boolean} - True if the object is an array, false otherwise
 * @link https://stackoverflow.com/questions/4775722/how-to-check-if-an-object-is-array
 */
function isArray(what) {
    return Object.prototype.toString.call(what) === '[object Array]';
}

/**
 * Add a header to the table.
 * @param table - The table to add the header to.
 */
const addTableHeader = (table) => {
    const row = table.insertRow();
    const idCell = row.insertCell(0);
    const firstNameCell = row.insertCell(1);
    const lastNameCell = row.insertCell(2);
    const genderCell = row.insertCell(3);
    const ageCell = row.insertCell(4);
    const emailCell = row.insertCell(5);
    const phoneCell = row.insertCell(6);

    idCell.innerHTML = "<b>ID</b>";
    firstNameCell.innerHTML = "<b>First Name</b>";
    lastNameCell.innerHTML = "<b>Last Name</b>";
    genderCell.innerHTML = "<b>Gender</b>";
    ageCell.innerHTML = "<b>Age</b>";
    emailCell.innerHTML = "<b>Email</b>";
    phoneCell.innerHTML = "<b>Phone</b>";
}

/**
 * Add users to the table.
 * @param users
 */
const addUserDataToTable = (users) => {
    // Check if there are users to add
    if(users.length === 0) {
        displayError("No users found");
    }

    // Display the table and hide the error message
    table_div.style.display = "block";
    errorMessage.style.display = "none";


    // Clear table if there are users
    const table = document.querySelector('table[class="table table-hover table-sm table-bordered"]');
    table.innerHTML = "";
    addTableHeader(table);

    // Add users to table
    const usersResult = JSON.parse(users);
    if(isArray(usersResult)) {
        usersResult.forEach(user => {
            addUserToTable(table, user);
        });
    } else {
        // For single user
        addUserToTable(table, usersResult);
    }
}

/**
 * Handles the onClick event of the search button.
 * @param {Event} e
 */
const onSearchUser = (e) => {
    e.preventDefault();

    let userName = document.forms[0].querySelector('input[name="username_search"]').value;
    const textArea = document.forms[0].querySelector('textarea[class="form-control"]');
    let apiUrl = "http://localhost:8080/api/v1/user";

    // Check if get all or get one
    if(userName.toString().length > 0) {
        try{
            userId = parseInt(userName);
            if(isNaN(userId)) {
                throw new Error("Not a number");
            }
            console.log("Searching by id");
            apiUrl += "/" + userId;
        } catch(e) {
            // Search by name
            apiUrl += "/name/" + userName;
        }
    }

    const fetchOptions = {
        method: 'GET'
    };

    /**
     * Fetches the user from the API.
     */
    const fetchData = async () => {
        const response = await fetch(apiUrl, fetchOptions);
        const code = response.status;

        if (code === 200) {
            const user = await response.json();
            console.log("We got a response: ", user);
            addUserDataToTable(JSON.stringify(user));
            // textArea.value = JSON.stringify(user);
        }
        else {
            console.log("We got a response: ", code);
            displayError("No users found");
        }
    }
    fetchData();
}

/**
 *
 * @param {string} url  - The URL to fetch.
 * @param {string} data - The json information
 * @returns
 */
const postData = async (url = '', data = {}) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        mode: 'cors',
        body: data
    };

    const response = await fetch(url, fetchOptions);
    return response.status;
}

/**
 * Handles the onClick event of the Add User button to send a POST request to the API.
 * @param {Event} e
 */
const onAddUser = (e) => {
    e.preventDefault();

    window.open("AddUser.html", "Add User", "width=500,height=500");

    // alert("Adding user");


    // const textArea = document.forms[0].querySelector('textarea[class="form-control"]')
    // let data = textArea.value;
    // console.log("Data: ", data);


    // const apiUrl = 'http://localhost:8080/api/v1/user/';
    //
    //
    // /**
    //  * Fetches the user from the API.
    //  */
    // const fetchData = async () => {
    //     const response = await postData(apiUrl, data);
    //     const code = response;
    //
    //     if (code === 201) {
    //         // const user = await response.json();
    //         // console.log("We got a response: ", user);
    //         textArea.value = "Successfully added user";
    //     } else {
    //         console.log("We got a response: ", code);
    //         textArea.value = "Failed to add user";
    //     }
    // }
    // fetchData();
}

/**
 * Manages the initialisation of the page.
 */
const onLoad = () => {
    // Hide elements

    table_div.style.display = "none";
    const table = document.querySelector('table[class="table table-striped"]');
    errorMessage.style.display = "none";
}

onLoad();