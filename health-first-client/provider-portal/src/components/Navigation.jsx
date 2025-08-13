import React from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  IconButton,
  Badge,
  Avatar,
  Menu,
  MenuItem,
  Chip
} from '@mui/material';
import {
  Search,
  Notifications,
  Person,
  KeyboardArrowDown,
  Message
} from '@mui/icons-material';

const Navigation = ({ 
  currentPage = 'dashboard', 
  onNavigate, 
  onLogout, 
  isGuestMode = false,
  userName = "John Doe"
}) => {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleProfileMenuClose = () => {
    setAnchorEl(null);
  };

  const navigationItems = [
    { key: 'dashboard', label: 'Dashboard' },
    { key: 'scheduling', label: 'Scheduling' },
    { key: 'patients', label: 'Patients' },
    { key: 'communications', label: 'Communications' },
    { key: 'billing', label: 'Billing' },
    { key: 'referral', label: 'Referral' },
    { key: 'reports', label: 'Reports' },
    { key: 'settings', label: 'Settings' }
  ];

  return (
    <AppBar 
      position="static" 
      elevation={0}
      sx={{
        background: '#1e3a8a',
        borderBottom: '1px solid rgba(255,255,255,0.1)'
      }}
    >
      <Toolbar sx={{ px: { xs: 2, md: 4 }, minHeight: 64 }}>
        {/* Logo and App Name */}
        <Box sx={{ display: 'flex', alignItems: 'center', flexGrow: 1 }}>
          <Typography 
            variant="h6" 
            sx={{ 
              fontWeight: 700, 
              color: 'white',
              fontSize: '1.25rem'
            }}
          >
            Sample EMR
          </Typography>
        </Box>

        {/* Navigation Links */}
        <Box sx={{ 
          display: { xs: 'none', md: 'flex' }, 
          alignItems: 'center', 
          gap: 1,
          mx: 2
        }}>
          {navigationItems.map((item) => (
            <Button
              key={item.key}
              color="inherit"
              onClick={() => onNavigate(item.key)}
              sx={{
                px: 2,
                py: 1,
                fontSize: '0.875rem',
                fontWeight: 500,
                textTransform: 'none',
                borderBottom: currentPage === item.key ? '2px solid white' : 'none',
                backgroundColor: currentPage === item.key ? 'rgba(255,255,255,0.1)' : 'transparent',
                '&:hover': {
                  backgroundColor: 'rgba(255,255,255,0.1)',
                }
              }}
            >
              {item.label}
            </Button>
          ))}
        </Box>

        {/* Right Side Icons and User */}
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          {/* Search Icon */}
          <IconButton 
            color="inherit" 
            size="small"
            sx={{ 
              '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' }
            }}
          >
            <Search />
          </IconButton>

          {/* Messages Icon */}
          <IconButton 
            color="inherit" 
            size="small"
            sx={{ 
              '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' }
            }}
          >
            <Badge badgeContent={2} color="error">
              <Message />
            </Badge>
          </IconButton>

          {/* Notifications Icon */}
          <IconButton 
            color="inherit" 
            size="small"
            sx={{ 
              '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' }
            }}
          >
            <Badge badgeContent={3} color="error">
              <Notifications />
            </Badge>
          </IconButton>

          {/* User Profile */}
          <Box sx={{ display: 'flex', alignItems: 'center', ml: 2 }}>
            <Avatar 
              sx={{ 
                width: 32, 
                height: 32, 
                bgcolor: 'rgba(255,255,255,0.2)',
                fontSize: '0.875rem'
              }}
            >
              {userName.split(' ').map(n => n[0]).join('')}
            </Avatar>
            <Button
              color="inherit"
              onClick={handleProfileMenuOpen}
              endIcon={<KeyboardArrowDown />}
              sx={{
                ml: 1,
                textTransform: 'none',
                fontSize: '0.875rem',
                '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' }
              }}
            >
              {userName}
            </Button>
            {isGuestMode && (
              <Chip 
                label="Guest" 
                size="small" 
                sx={{ 
                  ml: 1,
                  backgroundColor: 'rgba(255,255,255,0.2)', 
                  color: 'white',
                  fontSize: '0.7rem',
                  height: 20
                }} 
              />
            )}
          </Box>
        </Box>
      </Toolbar>

      {/* Profile Menu */}
      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleProfileMenuClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        PaperProps={{
          sx: {
            mt: 1,
            minWidth: 150,
            borderRadius: 2,
            boxShadow: '0 8px 32px rgba(0,0,0,0.12)'
          }
        }}
      >
        <MenuItem onClick={handleProfileMenuClose}>Profile</MenuItem>
        <MenuItem onClick={handleProfileMenuClose}>Settings</MenuItem>
        <MenuItem onClick={onLogout}>Logout</MenuItem>
      </Menu>
    </AppBar>
  );
};

export default Navigation;
