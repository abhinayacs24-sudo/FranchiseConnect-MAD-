const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    firstName: String,
    middleName: String,
    lastName: String,
    dob: String,
    city: String,
    pincode: String,
    address: String,
    occupation: String,
    mobile: String,
    email: { type: String, unique: true, required: true },
    password: { type: String, required: true },
    gender: String,
    state: String,
    qualification: String
});

module.exports = mongoose.model('User', UserSchema);
