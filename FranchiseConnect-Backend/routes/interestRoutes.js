const express = require('express');
const router = express.Router();
const interestController = require('../controllers/interestController');
const authMiddleware = require('../middleware/authMiddleware');

router.post('/', authMiddleware, interestController.expressInterest);
router.get('/', authMiddleware, interestController.getUserInterests);

module.exports = router;
