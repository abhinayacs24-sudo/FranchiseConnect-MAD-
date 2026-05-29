const express = require('express');
const router = express.Router();
const reportController = require('../controllers/reportController');
const authMiddleware = require('../middleware/authMiddleware');
const roleMiddleware = require('../middleware/roleMiddleware');

router.get('/generate', authMiddleware, roleMiddleware(['admin']), reportController.generateReport);

module.exports = router;
