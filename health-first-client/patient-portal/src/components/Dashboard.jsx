import { 
  Container, 
  Typography, 
  Box, 
  AppBar, 
  Toolbar, 
  Button, 
  Grid, 
  Card, 
  CardContent,
  Fade,
  Slide
} from '@mui/material';
import { 
  Person, 
  CalendarToday, 
  LocalHospital, 
  Medication,
  Logout,
  Dashboard as DashboardIcon,
  HealthAndSafety
} from '@mui/icons-material';

const Dashboard = ({ onLogout }) => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ background: 'linear-gradient(45deg, #1976d2, #42a5f5)' }}>
        <Toolbar>
          <Person sx={{ mr: 2 }} />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Patient Portal
          </Typography>
          <Button color="inherit" startIcon={<DashboardIcon />}>
            Dashboard
          </Button>
          <Button color="inherit" startIcon={<HealthAndSafety />}>
            Health Records
          </Button>
          <Button color="inherit" onClick={onLogout} startIcon={<Logout />}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>
      
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Slide direction="up" in={true} timeout={800}>
          <Box>
            <Fade in={true} timeout={1000}>
              <Typography variant="h3" component="h1" gutterBottom sx={{ 
                fontWeight: 600,
                background: 'linear-gradient(45deg, #1976d2, #42a5f5)',
                backgroundClip: 'text',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent'
              }}>
                Welcome to Your Health Dashboard
              </Typography>
            </Fade>
            
            <Fade in={true} timeout={1200}>
              <Typography variant="h6" color="text.secondary" paragraph>
                Manage your appointments, view medical records, and stay connected with your healthcare team
              </Typography>
            </Fade>
            
            <Grid container spacing={3} sx={{ mt: 4 }}>
              <Grid item xs={12} sm={6} md={3}>
                <Fade in={true} timeout={1400}>
                  <Card sx={{ 
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      transform: 'translateY(-8px)',
                      boxShadow: '0 8px 25px rgba(25, 118, 210, 0.2)',
                    }
                  }}>
                    <CardContent>
                      <CalendarToday sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                      <Typography variant="h4" component="div">
                        3
                      </Typography>
                      <Typography color="text.secondary">
                        Upcoming Appointments
                      </Typography>
                    </CardContent>
                  </Card>
                </Fade>
              </Grid>
              
              <Grid item xs={12} sm={6} md={3}>
                <Fade in={true} timeout={1600}>
                  <Card sx={{ 
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      transform: 'translateY(-8px)',
                      boxShadow: '0 8px 25px rgba(25, 118, 210, 0.2)',
                    }
                  }}>
                    <CardContent>
                      <LocalHospital sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                      <Typography variant="h4" component="div">
                        12
                      </Typography>
                      <Typography color="text.secondary">
                        Medical Records
                      </Typography>
                    </CardContent>
                  </Card>
                </Fade>
              </Grid>
              
              <Grid item xs={12} sm={6} md={3}>
                <Fade in={true} timeout={1800}>
                  <Card sx={{ 
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      transform: 'translateY(-8px)',
                      boxShadow: '0 8px 25px rgba(25, 118, 210, 0.2)',
                    }
                  }}>
                    <CardContent>
                      <Medication sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                      <Typography variant="h4" component="div">
                        5
                      </Typography>
                      <Typography color="text.secondary">
                        Active Prescriptions
                      </Typography>
                    </CardContent>
                  </Card>
                </Fade>
              </Grid>
              
              <Grid item xs={12} sm={6} md={3}>
                <Fade in={true} timeout={2000}>
                  <Card sx={{ 
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      transform: 'translateY(-8px)',
                      boxShadow: '0 8px 25px rgba(25, 118, 210, 0.2)',
                    }
                  }}>
                    <CardContent>
                      <HealthAndSafety sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                      <Typography variant="h4" component="div">
                        2
                      </Typography>
                      <Typography color="text.secondary">
                        Recent Tests
                      </Typography>
                    </CardContent>
                  </Card>
                </Fade>
              </Grid>
            </Grid>
            
            <Fade in={true} timeout={2200}>
              <Box sx={{ mt: 4, display: 'flex', gap: 2, flexWrap: 'wrap' }}>
                <Button 
                  variant="contained" 
                  startIcon={<CalendarToday />}
                  sx={{
                    background: 'linear-gradient(45deg, #1976d2, #42a5f5)',
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      transform: 'translateY(-2px)',
                      boxShadow: '0 6px 20px rgba(25, 118, 210, 0.4)',
                    }
                  }}
                >
                  Schedule Appointment
                </Button>
                <Button 
                  variant="outlined" 
                  startIcon={<LocalHospital />}
                  sx={{
                    borderColor: '#1976d2',
                    color: '#1976d2',
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      borderColor: '#1565c0',
                      backgroundColor: 'rgba(25, 118, 210, 0.04)',
                      transform: 'translateY(-2px)',
                    }
                  }}
                >
                  View Medical Records
                </Button>
                <Button 
                  variant="outlined" 
                  startIcon={<Medication />}
                  sx={{
                    borderColor: '#1976d2',
                    color: '#1976d2',
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      borderColor: '#1565c0',
                      backgroundColor: 'rgba(25, 118, 210, 0.04)',
                      transform: 'translateY(-2px)',
                    }
                  }}
                >
                  Prescriptions
                </Button>
                <Button 
                  variant="outlined"
                  startIcon={<HealthAndSafety />}
                  sx={{
                    borderColor: '#1976d2',
                    color: '#1976d2',
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      borderColor: '#1565c0',
                      backgroundColor: 'rgba(25, 118, 210, 0.04)',
                      transform: 'translateY(-2px)',
                    }
                  }}
                >
                  Lab Results
                </Button>
              </Box>
            </Fade>
          </Box>
        </Slide>
      </Container>
    </Box>
  );
};

export default Dashboard; 