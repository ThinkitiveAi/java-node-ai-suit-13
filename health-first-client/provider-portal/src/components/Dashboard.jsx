import { useState, useEffect } from 'react';
import { 
  Container, 
  Typography, 
  Box, 
  Grid, 
  Card, 
  CardContent,
  Fade,
  Slide,
  Alert,
  Button
} from '@mui/material';
import { 
  LocalHospital, 
  People, 
  Schedule, 
  Assignment,
  Add
} from '@mui/icons-material';
import ProvidersList from './ProvidersList';
import AddProviderModal from './AddProviderModal';
import { dummyProviders } from '../utils/providersData';

const Dashboard = ({ onLogout, isGuestMode = false }) => {
  const [providers, setProviders] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);

  useEffect(() => {
    // Load dummy data on component mount
    setProviders(dummyProviders);
  }, []);

  const handleAddProvider = (newProvider) => {
    setProviders(prev => [newProvider, ...prev]);
    setShowSuccessAlert(true);
    setTimeout(() => setShowSuccessAlert(false), 3000);
  };

  const handleOpenAddModal = () => {
    setShowAddModal(true);
  };

  const handleCloseAddModal = () => {
    setShowAddModal(false);
  };

  const handleViewProvider = (provider) => {
    // Handle view provider action
    console.log('View provider:', provider);
  };

  const handleEditProvider = (provider) => {
    // Handle edit provider action
    console.log('Edit provider:', provider);
  };

  const handleDeleteProvider = (provider) => {
    // Handle delete provider action
    setProviders(prev => prev.filter(p => p.id !== provider.id));
  };

  const statsCards = [
    {
      icon: <People sx={{ fontSize: 32, color: '#1976d2' }} />,
      value: providers.length,
      label: 'Active Providers',
      color: '#1976d2',
      gradient: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)'
    },
    {
      icon: <Schedule sx={{ fontSize: 32, color: '#2e7d32' }} />,
      value: 23,
      label: "Today's Appointments",
      color: '#2e7d32',
      gradient: 'linear-gradient(135deg, #2e7d32 0%, #4caf50 100%)'
    },
    {
      icon: <Assignment sx={{ fontSize: 32, color: '#ed6c02' }} />,
      value: 8,
      label: 'Pending Reports',
      color: '#ed6c02',
      gradient: 'linear-gradient(135deg, #ed6c02 0%, #ff9800 100%)'
    },
    {
      icon: <LocalHospital sx={{ fontSize: 32, color: '#d32f2f' }} />,
      value: 12,
      label: 'Emergency Cases',
      color: '#d32f2f',
      gradient: 'linear-gradient(135deg, #d32f2f 0%, #f44336 100%)'
    }
  ];

  return (
    <Box sx={{ flexGrow: 1, background: '#f8fafc', minHeight: '100vh' }}>
      {/* Main Content */}
      <Container maxWidth="xl" sx={{ py: 4, px: { xs: 2, md: 4 } }}>
        {/* Success Alert */}
        {showSuccessAlert && (
          <Fade in={true} timeout={500}>
            <Alert 
              severity="success" 
              sx={{ 
                mb: 4, 
                borderRadius: 3,
                boxShadow: '0 4px 20px rgba(76, 175, 80, 0.3)'
              }}
              onClose={() => setShowSuccessAlert(false)}
            >
              Provider added successfully!
            </Alert>
          </Fade>
        )}

        <Slide direction="up" in={true} timeout={800}>
          <Box>
            {/* Header Section */}
            <Fade in={true} timeout={1000}>
              <Box sx={{ mb: 4 }}>
                <Typography variant="h3" component="h1" gutterBottom sx={{ 
                  fontWeight: 700,
                  background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
                  backgroundClip: 'text',
                  WebkitBackgroundClip: 'text',
                  WebkitTextFillColor: 'transparent',
                  mb: 1
                }}>
                  Healthcare Provider Dashboard
                </Typography>
                <Typography variant="h6" color="text.secondary" sx={{ mb: 3 }}>
                  Manage patients, appointments, and medical records
                </Typography>
                
                {/* Action Buttons */}
                <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', mb: 4 }}>
                  <Button 
                    variant="contained" 
                    startIcon={<Add />}
                    onClick={handleOpenAddModal}
                    sx={{
                      px: 3,
                      py: 1.5,
                      fontSize: '0.9rem',
                      fontWeight: 600,
                      boxShadow: '0 4px 20px rgba(25, 118, 210, 0.3)',
                      '&:hover': {
                        transform: 'translateY(-2px)',
                        boxShadow: '0 8px 30px rgba(25, 118, 210, 0.4)',
                      }
                    }}
                  >
                    Add Clinician
                  </Button>
                  <Button 
                    variant="outlined" 
                    startIcon={<People />}
                    sx={{
                      px: 3,
                      py: 1.5,
                      fontSize: '0.9rem',
                      fontWeight: 600,
                      borderColor: '#1976d2',
                      color: '#1976d2',
                      '&:hover': {
                        borderColor: '#1565c0',
                        backgroundColor: 'rgba(25, 118, 210, 0.04)',
                        transform: 'translateY(-2px)',
                      }
                    }}
                  >
                    View Patients
                  </Button>
                  <Button 
                    variant="outlined" 
                    startIcon={<Schedule />}
                    sx={{
                      px: 3,
                      py: 1.5,
                      fontSize: '0.9rem',
                      fontWeight: 600,
                      borderColor: '#1976d2',
                      color: '#1976d2',
                      '&:hover': {
                        borderColor: '#1565c0',
                        backgroundColor: 'rgba(25, 118, 210, 0.04)',
                        transform: 'translateY(-2px)',
                      }
                    }}
                  >
                    Manage Appointments
                  </Button>
                  <Button 
                    variant="outlined" 
                    startIcon={<Assignment />}
                    sx={{
                      px: 3,
                      py: 1.5,
                      fontSize: '0.9rem',
                      fontWeight: 600,
                      borderColor: '#1976d2',
                      color: '#1976d2',
                      '&:hover': {
                        borderColor: '#1565c0',
                        backgroundColor: 'rgba(25, 118, 210, 0.04)',
                        transform: 'translateY(-2px)',
                      }
                    }}
                  >
                    Medical Records
                  </Button>
                </Box>
              </Box>
            </Fade>
            
            {/* Stats Cards */}
            <Grid container spacing={3} sx={{ mb: 6 }}>
              {statsCards.map((card, index) => (
                <Grid item xs={12} sm={6} md={3} key={index}>
                  <Fade in={true} timeout={1400 + index * 200}>
                    <Card sx={{ 
                      height: '100%',
                      background: card.gradient,
                      color: 'white',
                      transition: 'all 0.4s cubic-bezier(0.4, 0, 0.2, 1)',
                      '&:hover': {
                        transform: 'translateY(-8px) scale(1.02)',
                        boxShadow: `0 20px 40px ${card.color}40`,
                      }
                    }}>
                      <CardContent sx={{ p: 3 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                          {card.icon}
                          <Box sx={{ ml: 'auto' }}>
                            <Typography variant="h4" component="div" sx={{ fontWeight: 700 }}>
                              {card.value}
                            </Typography>
                          </Box>
                        </Box>
                        <Typography variant="body1" sx={{ fontWeight: 500, opacity: 0.9 }}>
                          {card.label}
                        </Typography>
                      </CardContent>
                    </Card>
                  </Fade>
                </Grid>
              ))}
            </Grid>

            {/* Providers List */}
            <Box>
              <ProvidersList 
                providers={providers} 
                onViewProvider={handleViewProvider}
                onEditProvider={handleEditProvider}
                onDeleteProvider={handleDeleteProvider}
              />
            </Box>
          </Box>
        </Slide>
      </Container>

      {/* Add Provider Modal */}
      <AddProviderModal
        open={showAddModal}
        onClose={handleCloseAddModal}
        onAddProvider={handleAddProvider}
      />
    </Box>
  );
};

export default Dashboard; 