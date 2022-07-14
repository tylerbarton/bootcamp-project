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
    // Get the user id from the row that this button is in
    const userId = e.target.parentElement.id;
    const row = table.querySelector(`tr[id="${userId}"]`);

    // Get the user information from the row
    const firstName = row.cells[1].innerHTML;
    const lastName = row.cells[2].innerHTML;
    const gender = row.cells[3].innerHTML;
    const age = row.cells[4].innerHTML;
    const email = row.cells[5].innerHTML;
    const phone = row.cells[6].innerHTML;

    // Generate the object to PUT
    const body = {
        firstName: firstName,
        lastName: lastName,
        age: age,
        gender: gender,
        emailAddress: Array({
            address: email.toString(),
            type: "home"
        }),
        phoneNumber: Array({
            number: phone.toString(),
            type: "home"
        })
    }

    // Update the user with a PUT request
    const url = `http://localhost:8080/api/v1/user/${userId}`;
    const options = {
        method: 'PUT',
        headers: {
            'Authorization': auth_token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }

    fetch(url, options)
        .then(response => {
            if(response.status === 204) {
                // OK
            } else {
                displayError("Error updating user");
            }
        });
}

/**
 * Visually remove the user from the table.
 * @param table
 * @param userId
 */
removeUserFromTable = (table, userId) => {
    // Remove the row with the user id from the table
    const row = table.querySelector(`tr[id="${userId}"]`);
    row.remove();
}

/**
 * Handles the onClick event of the Remove User button to send a DELETE request to the API.
 * @param e
 */
onRemoveUser = (e) => {
    // Get the user id from the row that this button is in
    const userId = e.parentElement.parentElement.id;



    // Send a DELETE request to the API
    const url = `http://localhost:8080/api/v1/user/${userId}`;
    const options = {
        method: 'DELETE',
        headers: {
            'Authorization': auth_token
        }
    }
    fetch(url, options)
        .then(response => {
            if(response.status === 204) {
                // Remove the user from the table
                removeUserFromTable(table, userId);

                // Intentionally display an error to slow the user down
                displayError("User removed");
            } else {
                displayError("Error removing user");
            }
        }).catch(error => {
            displayError("Error removing user");
        });
}

/**
 * Helper method to add a user to the search table.
 * @param table - The table to add the user to.
 * @param user - The user to add.
 */
const addUserToTable = (tbody, user) => {
    // Define the cells
    const row = tbody.insertRow();

    // Add the user id to the row
    row.id = user.id;

    // Add the user information to the row
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

    // Hook the cells to the onEditUser function
    for (let i = 0; i < row.cells.length-1; i++) {
        row.cells[i].addEventListener('blur', onEditUser);
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