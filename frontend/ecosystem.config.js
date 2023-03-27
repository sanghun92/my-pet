module.exports = {
  apps: [
    {
      name: 'MyPetFrontApp',
      exec_mode: 'fork',
      instances: '1', // Or a number of instances
      script: 'node_modules/next/dist/bin/next',
      args: 'start',
      env_local: {
        NODE_ENV: 'local',
      },
      env_development: {
        NODE_ENV: 'development',
      },
      env_production: {
        NODE_ENV: 'production',
      },
    },
  ],
};
