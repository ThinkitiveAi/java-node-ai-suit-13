import { useState } from 'react';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Box,
  Chip,
  Fade,
  Slide,
  IconButton,
  Tooltip,
  Avatar,
  Button,
  Menu,
  MenuItem,
  ListItemIcon,
  ListItemText
} from '@mui/material';
import {
  Email,
  Phone,
  LocationOn,
  Person,
  LocalHospital,
  MoreVert,
  Edit,
  Delete,
  Visibility,
  Security,
  AccountCircle
} from '@mui/icons-material';

const ProvidersList = ({ providers, onEditProvider, onDeleteProvider, onViewProvider }) => {
  const [hoveredRow, setHoveredRow] = useState(null);
  const [anchorEl, setAnchorEl] = useState(null);
  const [selectedProvider, setSelectedProvider] = useState(null);

  const getInitials = (firstName, lastName) => {
    return `${firstName.charAt(0)}${lastName.charAt(0)}`.toUpperCase();
  };

  const getSpecializationColor = (specialization) => {
    const colors = {
      'Cardiology': '#f44336',
      'Neurology': '#2196f3',
      'Pediatrics': '#4caf50',
      'Orthopedics': '#ff9800',
      'Dermatology': '#9c27b0',
      'General Practice': '#607d8b',
      'Gynecology': '#e91e63',
      'Oncology': '#795548',
      'Ophthalmology': '#00bcd4',
      'Psychiatry': '#8bc34a',
      'Radiology': '#ff5722',
      'Surgery': '#3f51b5',
      'Urology': '#009688',
      'Endocrinology': '#ffc107',
      'Gastroenterology': '#673ab7'
    };
    return colors[specialization] || '#607d8b';
  };

  const handleMenuOpen = (event, provider) => {
    setAnchorEl(event.currentTarget);
    setSelectedProvider(provider);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    setSelectedProvider(null);
  };

  const handleAction = (action) => {
    if (selectedProvider) {
      switch (action) {
        case 'view':
          onViewProvider?.(selectedProvider);
          break;
        case 'edit':
          onEditProvider?.(selectedProvider);
          break;
        case 'delete':
          onDeleteProvider?.(selectedProvider);
          break;
      }
    }
    handleMenuClose();
  };

  return (
    <Fade in={true} timeout={800}>
      <Box>
        <Slide direction="up" in={true} timeout={600}>
          <Box sx={{ mb: 4 }}>
            <Typography variant="h4" component="h2" gutterBottom sx={{ 
              fontWeight: 600,
              color: 'primary.main',
              display: 'flex',
              alignItems: 'center',
              gap: 1
            }}>
              <LocalHospital />
              Healthcare Providers
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Manage and view all registered healthcare providers
            </Typography>
          </Box>
        </Slide>

        <Slide direction="up" in={true} timeout={800}>
          <Paper 
            elevation={0} 
            sx={{ 
              borderRadius: 4,
              overflow: 'hidden',
              border: '1px solid rgba(0, 0, 0, 0.08)',
              boxShadow: '0 4px 20px rgba(0,0,0,0.08)',
              transition: 'all 0.3s ease',
              '&:hover': {
                boxShadow: '0 8px 30px rgba(0,0,0,0.12)',
              }
            }}
          >
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow sx={{ 
                    background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
                    '& th': {
                      color: 'white',
                      fontWeight: 600,
                      fontSize: '0.875rem',
                      borderBottom: 'none',
                      py: 2
                    }
                  }}>
                    <TableCell>Provider</TableCell>
                    <TableCell>Specialization</TableCell>
                    <TableCell>Contact</TableCell>
                    <TableCell>License</TableCell>
                    <TableCell>Location</TableCell>
                    <TableCell align="center">Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {providers.map((provider, index) => (
                    <Fade in={true} timeout={1000 + index * 100} key={provider.id}>
                      <TableRow
                        sx={{
                          transition: 'all 0.3s ease',
                          cursor: 'pointer',
                          backgroundColor: hoveredRow === provider.id ? 'rgba(25, 118, 210, 0.04)' : 'transparent',
                          '&:hover': {
                            backgroundColor: 'rgba(25, 118, 210, 0.08)',
                            transform: 'translateY(-1px)',
                            boxShadow: '0 4px 12px rgba(25, 118, 210, 0.15)',
                          },
                          '&:last-child td': {
                            borderBottom: 0
                          }
                        }}
                        onMouseEnter={() => setHoveredRow(provider.id)}
                        onMouseLeave={() => setHoveredRow(null)}
                      >
                        <TableCell sx={{ py: 2 }}>
                          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                            <Avatar 
                              sx={{ 
                                bgcolor: getSpecializationColor(provider.specialization),
                                width: 48,
                                height: 48,
                                fontSize: '1.2rem',
                                fontWeight: 600,
                                boxShadow: '0 4px 12px rgba(0,0,0,0.15)'
                              }}
                            >
                              {getInitials(provider.firstName, provider.lastName)}
                            </Avatar>
                            <Box>
                              <Typography variant="subtitle1" sx={{ fontWeight: 600, color: 'text.primary' }}>
                                {provider.firstName} {provider.lastName}
                              </Typography>
                              <Typography variant="body2" color="text.secondary">
                                {provider.yearsExperience} years experience
                              </Typography>
                            </Box>
                          </Box>
                        </TableCell>
                        
                        <TableCell>
                          <Chip
                            label={provider.specialization}
                            sx={{
                              backgroundColor: getSpecializationColor(provider.specialization),
                              color: 'white',
                              fontWeight: 600,
                              fontSize: '0.75rem',
                              height: 24,
                              '&:hover': {
                                opacity: 0.9,
                              }
                            }}
                          />
                        </TableCell>
                        
                        <TableCell>
                          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 0.5 }}>
                            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                              <Email sx={{ fontSize: 14, color: 'action.active' }} />
                              <Typography variant="body2" sx={{ fontSize: '0.8rem' }}>
                                {provider.email}
                              </Typography>
                            </Box>
                            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                              <Phone sx={{ fontSize: 14, color: 'action.active' }} />
                              <Typography variant="body2" sx={{ fontSize: '0.8rem' }}>
                                {provider.phone}
                              </Typography>
                            </Box>
                          </Box>
                        </TableCell>
                        
                        <TableCell>
                          <Typography variant="body2" sx={{ 
                            fontWeight: 600,
                            color: 'primary.main',
                            fontFamily: 'monospace',
                            fontSize: '0.8rem'
                          }}>
                            {provider.licenseNumber}
                          </Typography>
                        </TableCell>
                        
                        <TableCell>
                          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                            <LocationOn sx={{ fontSize: 14, color: 'action.active' }} />
                            <Typography variant="body2" sx={{ fontSize: '0.8rem' }}>
                              {provider.city}, {provider.state}
                            </Typography>
                          </Box>
                        </TableCell>

                        <TableCell align="center">
                          <Box sx={{ display: 'flex', justifyContent: 'center', gap: 1 }}>
                            <Tooltip title="View Details">
                              <IconButton
                                size="small"
                                onClick={() => onViewProvider?.(provider)}
                                sx={{
                                  color: 'primary.main',
                                  '&:hover': {
                                    backgroundColor: 'rgba(25, 118, 210, 0.1)',
                                    transform: 'scale(1.1)',
                                  }
                                }}
                              >
                                <Visibility fontSize="small" />
                              </IconButton>
                            </Tooltip>
                            
                            <Tooltip title="Edit Provider">
                              <IconButton
                                size="small"
                                onClick={() => onEditProvider?.(provider)}
                                sx={{
                                  color: 'warning.main',
                                  '&:hover': {
                                    backgroundColor: 'rgba(255, 152, 0, 0.1)',
                                    transform: 'scale(1.1)',
                                  }
                                }}
                              >
                                <Edit fontSize="small" />
                              </IconButton>
                            </Tooltip>

                            <Tooltip title="More Actions">
                              <IconButton
                                size="small"
                                onClick={(e) => handleMenuOpen(e, provider)}
                                sx={{
                                  color: 'text.secondary',
                                  '&:hover': {
                                    backgroundColor: 'rgba(0, 0, 0, 0.1)',
                                    transform: 'scale(1.1)',
                                  }
                                }}
                              >
                                <MoreVert fontSize="small" />
                              </IconButton>
                            </Tooltip>
                          </Box>
                        </TableCell>
                      </TableRow>
                    </Fade>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Slide>

        {/* Action Menu */}
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
          PaperProps={{
            sx: {
              borderRadius: 2,
              boxShadow: '0 8px 32px rgba(0,0,0,0.12)',
              border: '1px solid rgba(0,0,0,0.08)',
              minWidth: 180
            }
          }}
        >
          <MenuItem onClick={() => handleAction('view')}>
            <ListItemIcon>
              <Visibility fontSize="small" />
            </ListItemIcon>
            <ListItemText>View Details</ListItemText>
          </MenuItem>
          <MenuItem onClick={() => handleAction('edit')}>
            <ListItemIcon>
              <Edit fontSize="small" />
            </ListItemIcon>
            <ListItemText>Edit Provider</ListItemText>
          </MenuItem>
          <MenuItem onClick={() => handleAction('delete')} sx={{ color: 'error.main' }}>
            <ListItemIcon>
              <Delete fontSize="small" sx={{ color: 'error.main' }} />
            </ListItemIcon>
            <ListItemText>Delete Provider</ListItemText>
          </MenuItem>
        </Menu>
      </Box>
    </Fade>
  );
};

export default ProvidersList; 