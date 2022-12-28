<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
        <v-btn
            depressed
            color="primary"
        >
          <v-icon>mdi-brush</v-icon>
          Paint Application
        </v-btn>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
