// const table_div = document.querySelector('div[class="table_information"]');
const table_div = document.querySelector('div[class="table-editable"]');
const table = document.querySelector('table[class="table table-bordered table-responsive-md table-striped text-center"]');
const errorMessage = document.querySelector('p[class="user_error fw-bold text-danger shake animated"]');

// Authentication
const auth_user = 'admin'; //'user';
const auth_pass = 'admin'; //'11bd0a707e58-44ac85e717ec0aa5cb96';
const auth_token = 'Basic ' + btoa(auth_user + ':' + auth_pass);

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
 * Handles an edit made to the table and sends a PUT request to the API.
 * @param e
 */
const onEditUser = (e) => {
    // Get the user id
    const userId = e.target.parentElement.parentElement.id;

    // Get the user data
    const user = getUser(userId);

    // Add the user to the edit table
    addUserToEditTable(table, user);
}

/**
 * Handles the onClick event of the Remove User button to send a DELETE request to the API.
 * @param e
 */
onRemoveUser = (e) => {
    // Get the user id
    const userId = e.target.parentElement.parentElement.id;

    // Remove the user from the table
    removeUserFromTable(table, userId);
}

/**
 * Helper method to add a user to the search table.
 * @param table - The table to add the user to.
 * @param user - The user to add.
 */
const addUserToTable = (tbody, user) => {
    // Create cells with properties of <td class="pt-3-half" contenteditable="true"> for each cell

    // Define the cells
    const row = tbody.insertRow();
    const idCell = row.insertCell(0);
    const firstNameCell = row.insertCell(1);
    const lastNameCell = row.insertCell(2);
    const genderCell = row.insertCell(3);
    const ageCell = row.insertCell(4);
    const emailCell = row.insertCell(5);
    const phoneCell = row.insertCell(6);
    const removeCell = row.insertCell(7);

    // For each td element in the row, add the contenteditable attribute
    for (let i = 0; i < row.cells.length-1; i++) {
        row.cells[i].contentEditable = true;
        row.cells[i].className = "pt-3-half";
    }

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

    // Add the remove cell button that will remove the user from the table that includes the user id
    removeCell.innerHTML = "<button class='btn btn-danger btn-sm' onclick='onRemoveUser(this)'>Remove</button>";
    removeCell.className = "pt-3-half";
    removeCell.id = user.id;
}

/**
 * Helper function to check if the object is an array or not
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
    const header = table.createTHead();
    const row = header.insertRow();
    const idCell = row.insertCell(0);
    const firstNameCell = row.insertCell(1);
    const lastNameCell = row.insertCell(2);
    const genderCell = row.insertCell(3);
    const ageCell = row.insertCell(4);
    const emailCell = row.insertCell(5);
    const phoneCell = row.insertCell(6);
    const removeCell = row.insertCell(7);

    idCell.innerHTML = "<b>ID</b>";
    firstNameCell.innerHTML = "<b>First Name</b>";
    lastNameCell.innerHTML = "<b>Last Name</b>";
    genderCell.innerHTML = "<b>Gender</b>";
    ageCell.innerHTML = "<b>Age</b>";
    emailCell.innerHTML = "<b>Email</b>";
    phoneCell.innerHTML = "<b>Phone</b>";
    removeCell.innerHTML = "<b>Remove</b>";
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
    // const table = document.querySelector('table[class="table table-hover table-sm table-bordered"]');
    table.innerHTML = "";
    addTableHeader(table);
    const tbody = table.createTBody();

    // Add users to table
    const usersResult = JSON.parse(users);
    if(isArray(usersResult)) {
        usersResult.forEach(user => {
            addUserToTable(tbody, user);
        });
    } else {
        // For single user
        addUserToTable(tbody, usersResult);
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
        method: 'GET',
        headers: {
            'Authorization': auth_token
        },
        // mode: 'cors'
    };

    /**
     * Fetches the user from the API.
     */
    const fetchData = async () => {
        console.log("Token: " + auth_token);
        const response = await fetch(apiUrl, fetchOptions);
        const code = response.status;

        if (code === 200) {
            const user = await response.json();
            console.log("We got a response: ", user);
            addUserDataToTable(JSON.stringify(user));
            // textArea.value = JSON.stringify(user);
        }
        else if (code === 401) {
            displayError("Unauthorized");
        }
        else {
            console.log("We got a response: ", code);
            displayError("No users found");
        }
    }
    fetchData();
}

/**
 * Handles the onClick event of the Add User button to send a POST request to the API.
 * @param {Event} e
 */
const onAddUser = (e) => {
    e.preventDefault();
    window.open("AddUser.html", "Add User", "width=500,height=500");
}

/**
 * Manages the initialisation of the page.
 */
const onLoad = () => {
    // Check if the url contains User.html
    if(window.location.href.indexOf("User.html") <= -1) {
        return;
    }

    // Hide elements
    table_div.style.display = "none";
    errorMessage.style.display = "none";

    // const table = document.querySelector('table[class="table table-striped"]');
}

onLoad();