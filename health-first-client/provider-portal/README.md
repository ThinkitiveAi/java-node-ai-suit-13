# Provider Portal

A modern healthcare provider management system built with React and Material-UI.

## Features

### 🔐 Authentication
- **Provider Login**: Secure login for registered healthcare providers
- **Guest Login**: Direct access to dashboard without authentication
- **Registration**: Complete provider registration with multi-step form

### 📊 Dashboard
- **Overview Cards**: Key metrics and statistics
- **Provider Management**: View and manage healthcare providers
- **Add Clinician**: Add new providers through a comprehensive form
- **Guest Mode**: Special indicator for guest users

### 👥 Provider Management
- **Providers List**: Clean table display with key provider information
- **Add Provider**: Multi-step form for adding new providers
- **Real-time Validation**: Form validation with immediate feedback
- **Responsive Design**: Works on all device sizes

### 🎨 UI/UX Features
- **Modern Design**: Clean, professional healthcare interface
- **Micro-animations**: Smooth transitions and hover effects
- **Color-coded Specializations**: Visual distinction for different medical fields
- **Avatar System**: Provider initials with specialization colors
- **Success Notifications**: Toast alerts for user actions

## Technology Stack

- **React 19**: Latest React with hooks
- **Material-UI 7**: Modern component library
- **Vite**: Fast build tool
- **ESLint**: Code quality and consistency

## Getting Started

1. **Install Dependencies**
   ```bash
   npm install
   ```

2. **Start Development Server**
   ```bash
   npm run dev
   ```

3. **Build for Production**
   ```bash
   npm run build
   ```

## Usage

### Guest Login
- Click "Continue as Guest" on the login page
- Access dashboard immediately without authentication
- All features available in guest mode

### Provider Login
- Use existing provider credentials
- Full access to all features
- Secure authentication flow

### Adding Providers
- Click "Add Clinician" button in dashboard
- Complete the multi-step form
- Providers are added to the list immediately
- No backend required - frontend-only state management

## File Structure

```
src/
├── components/
│   ├── LoginForm.jsx          # Enhanced with guest login
│   ├── RegistrationForm.jsx   # Provider registration
│   ├── Dashboard.jsx          # Main dashboard with providers
│   ├── ProvidersList.jsx      # Providers table component
│   └── AddProviderModal.jsx   # Add provider modal
├── utils/
│   └── providersData.js       # Dummy data and utilities
└── App.jsx                    # Main app with routing
```

## Key Components

### ProvidersList
- Displays providers in a clean table format
- Color-coded specializations
- Avatar system with provider initials
- Hover effects and animations
- Responsive design

### AddProviderModal
- Reuses RegistrationForm logic
- Multi-step form (Personal, Professional, Address)
- Real-time validation
- No password fields (for provider management only)

### Dashboard
- Enhanced with provider management
- Guest mode indicator
- Success notifications
- Dynamic provider count

## Features Implemented

✅ **Guest Login Flow** - Direct dashboard access  
✅ **Providers List** - Clean table with key fields  
✅ **Add Clinician** - Modal form in dashboard  
✅ **Frontend-only** - No backend APIs needed  
✅ **Clean UI** - Modern design with animations  
✅ **Great UX** - Micro-animations and smooth transitions  
✅ **Reuse Existing Form** - RegistrationForm logic reused  
✅ **Dummy Data** - Initial providers for demonstration  

## Future Enhancements

- Search and filter providers
- Edit/delete provider functionality
- Export provider data
- Advanced analytics dashboard
- Patient management integration
