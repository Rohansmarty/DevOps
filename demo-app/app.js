const express = require('express');
const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(express.json());

// Routes
app.get('/', (req, res) => {
    res.json({
        message: 'ðŸš€ DevOps CI/CD Pipeline Demo App',
        status: 'Running',
        timestamp: new Date().toISOString(),
        environment: process.env.NODE_ENV || 'development',
        version: '1.0.0',
        endpoints: {
            health: '/health',
            info: '/info',
            status: '/status'
        }
    });
});

app.get('/health', (req, res) => {
    res.json({
        status: 'healthy',
        uptime: process.uptime(),
        timestamp: new Date().toISOString(),
        version: '1.0.0'
    });
});

app.listen(port, () => {
    console.log(ðŸš€ Demo App running on port );
});
