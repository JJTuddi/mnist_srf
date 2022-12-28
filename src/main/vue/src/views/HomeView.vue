<template>
  <div class="flex">
    <v-snackbar
        top
        right
        outlined
      :color="snackbar.color"
      v-model="snackbar.state"
        :timeout="snackbar.timeout"
    >
      {{ snackbar.message }}
      <ul>
        <li
            v-for="detail in snackbar.details" :key="detail[0]"
        > <strong> {{ detail[0] }} </strong>: {{ detail[1] }} </li>
      </ul>
    </v-snackbar>
    <div class="flex">
      <v-container>
        <v-row>
          <v-checkbox
              v-model="trainingMode"
              label="Train"
          ></v-checkbox>
          <v-spacer/>
          <v-expand-transition>
            <v-select
                v-model="digitToTrain"
                v-if="trainingMode"
                :items="[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]"
                label="Digit"
            ></v-select>
          </v-expand-transition>
          <v-scale-transition>
            <v-btn
                class="mt-3 ml-2"
                color="primary"
                v-if="trainingMode"
                @click="sendToTraining"
            >
              <v-icon>mdi-send</v-icon>
              Send To Training
            </v-btn>
          </v-scale-transition>
        </v-row>
        <v-row class="my-4">
          <v-spacer/>
          <div class="canvas">
            <vue-drawing-canvas
                ref="VueCanvasDrawing"
                :eraser="eraser"
                :fill-shape="fillShape"
                :image.sync="image"
                :initial-image="initialImage"
                :line-cap="lineCap"
                :line-join="lineJoin"
                :lineWidth="line"
                :stroke-type="strokeType"
                :styles="{
                    border: 'solid 1px #000',
                 }"
                height="600"
                saveAs="png"
                width="600"
                :output-width="28"
                :output-height="28"
            >
            </vue-drawing-canvas>
          </div>
          <v-spacer/>
        </v-row>
        <v-row class="mb-2">
          <v-btn
              class="mr-1"
              color="primary"
              @click="eraser = !eraser"
          >
            <v-icon small> {{ eraser ? 'mdi-eraser' : 'mdi-pencil' }}</v-icon>
            {{ eraser ? 'Eraser' : 'Draw' }}
          </v-btn>
          <v-btn
              class="mr-1"
              color="primary"
              @click="$refs.VueCanvasDrawing.reset()"
          >
            <v-icon small>mdi-reload</v-icon>
            Reset
          </v-btn>
          <v-spacer/>
          <v-btn
              outlined
              depressed
              class="mx-1"
              @click="copyToClipboard"
              color="primary"
          >
            <v-icon> mdi-clipboard </v-icon>
          </v-btn>
          <v-btn
              outlined
              depressed
              class="mx-1"
              @click="predict"
              color="primary"
          >
            <v-icon> mdi-tag-search-outline </v-icon>
          </v-btn>
          <v-spacer/>
          <v-btn
              class="ml-1"
              color="blue lighten-2"
              @click="$refs.VueCanvasDrawing.undo()"
          >
            <v-icon small>mdi-undo</v-icon>
            Undo
          </v-btn>
          <v-btn
              class="ml-1"
              color="blue lighten-2"
              @click="$refs.VueCanvasDrawing.redo()"
          >
            <v-icon small>mdi-redo</v-icon>
            Redo
          </v-btn>
        </v-row>
        <v-row>
          <v-slider
              v-model="line"
              :min="50"
              :max="75"
              label="Line width"
          ></v-slider>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import VueDrawingCanvas from "vue-drawing-canvas";
import axios from "axios";

const createSnackbar = (message, details = {}, color = "green", timeout = 4000) => ({message, color, timeout, details, state: true});

export default {
  name: 'HomeView',
  components: {
    VueDrawingCanvas,
  },
  data() {
    return {
      image: "",
      initialImage: [
        {
          type: "dash",
          from: {
            x: 262,
            y: 154,
          },
          coordinates: [],
          color: "#000000",
          width: 5,
          fill: false,
        },
      ],
      strokeType: "dash",
      lineCap: "round",
      lineJoin: "round",
      line: 55,
      eraser: false,
      fillShape: false,
      digitToTrain: 0,
      trainingMode: false,
      snackbar: {
        color: "green",
        state: false,
        timeout: 1,
        message: "",
      }
    }
  },
  methods: {
    copyToClipboard() {
      this.snackbar = createSnackbar("Image successfully copied to clipboard!");
      navigator.clipboard.writeText(this.rawImage);
    },
    async predict() {
      const data = (await axios.post(this.predictionUrl, this.rawImage, {
        headers: {
          'Content-Type': 'text/plain'
        }
      })).data;
      this.snackbar = createSnackbar("Predicted digit is ", Object.entries(data));
    },
    sendToTraining() {
      this.$refs.VueCanvasDrawing.reset();
    }
  },
  computed: {
    rawImage() {
      return this.image.replace("data:image/png;base64,", "");
    },
    predictionUrl() {
      return new URL('/api/predict', "http://192.168.1.7:8080").toString();
    },
  }
}
</script>

<style scoped>

.flex {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

</style>
