<template>
  <div class="main-beer-card">
    <router-link :to="{ name: 'beer', params: { beerId: card.beerId } }">
      <div class="card-container">
        <img :src="card.image" alt="brewery1" />
        <div class="card-info-wrapper">
          <div class="card-info">
            <div class="name">
              {{ card.beerName }}
            </div>
            <div class="brewery-details">
              <div>
                {{ card.beerType }}
              </div>
              <div>
                {{ card.tastingNotes }}
              </div>
              <div>
                {{ card.abv }}
              </div>
            </div>
          </div>
        </div>

        <div class="footer">
          <div class="star-ratings" v-if="card.averageRating > 0">
            <div
              class="fill-ratings"
              :style="{
                width: ((card.averageRating / 5) * 100).toFixed(2) + '%',
              }"
            >
              <span>★★★★★</span>
            </div>
            <div class="empty-ratings">
              <span>★★★★★</span>
            </div>

            <span
              :class="{
                'filled-hearts': isLiked,
                'empty-hearts': !isLiked,
              }"
              @click.prevent="toggleLike(isLiked)"
              style="cursor: pointer"
            >
              ♥
            </span>
          </div>

          <div v-else class="no-review">
            <div class="no-review-text">Add Review</div>
            <span
              :class="{
                'filled-hearts': isLiked,
                'empty-hearts': !isLiked,
              }"
              @click.prevent="toggleLike(isLiked)"
              style="cursor: pointer"
            >
              ♥
            </span>
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
// import BeerReviewList from './BeerReviewList.vue'
import beerService from "../services/BeerService";

export default {
  props: ["card"],
  data() {
    return {
      liked: {
        beerId: this.card.beerId,
        userId: this.$store.state.user.id,
      },
      userLiked: {},
    };
  },
  computed: {
    isLiked() {
      let likedBeer = this.$store.state.userLiked.find((beer) => {
        if (beer.beerId == this.card.beerId) {
          return beer;
        }
      });
      if (likedBeer === undefined) {
        return false;
      } else if (this.card.beerId === likedBeer.beerId) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    toggleLike(isLiked) {
      if (!isLiked) {
        beerService
          .addLikedBeer(this.card.beerId, this.$store.state.user.id)
          .then((response) => {
            if (response.status === 202) {
              // if (!this.$store.state.likedBeers.includes(this.liked)) {
              //   this.$store.commit("SET_LIKE_BEERS", this.liked);
          
              // }
              console.log('add')
              if(!this.$store.state.userLiked.includes(this.liked)){
                console.log("userLiked")
                    this.$store.commit("SET_USER_LIKED", this.liked)
              }
            }
          })
          .catch((error) => {
            this.errorMsg = "Could not add beer" + error.response.status;
          });
      } else {
        beerService.deleteLikedBeer(this.$store.state.user.id, this.card.beerId).then((response) => {
        if (response.status === 200){
          console.log('delete')
        this.$store.commit("DELETE_USER_LIKED", this.card.beerId);
        // this.$store.commit("DELETE_LIKE_BEERS", this.card.beerId);
          }
        })
        .catch((error) => {
          this.errorMsg = "Could not delete" + error.response.status;
        });
      }
    },
  },
  created() {
  //   beerService.getLikedBeers(this.$store.state.user).then((response) => {
  //     response.data.forEach((element) => {
  //       // this.$store.state.likedBeers = [];
  //       this.userLiked.userId = this.$store.state.user.id;
  //       this.userLiked.beerId = element.beerId;
      
  //       this.$store.commit("SET_LIKE_BEERS", this.userLiked);
  //       this.userLiked = {};
  //     });
  //   });
  // }
  // this.$store.state.likedBeers = this.$store.state.userLiked
  }
};
</script>

<style scoped>
.main-beer-card {
  box-shadow: 1rem 1rem 4rem rgba(71, 74, 95, 0.623);
  border-radius: 4px;
  overflow: hidden;
  min-width: 300px;
}

a {
  height: 100%;
}

.card-container {
  display: flex;
  flex-direction: column;
  position: relative;
  margin-top: 1rem;
}

img {
  max-width: 100%;
  max-height: 200px;
  object-fit: scale-down;
}

.card-info-wrapper {
  position: absolute;
  bottom: 2rem; /* Position at the bottom */
  left: 0; /* Position at the left */
  width: 100%;
  background-color: rgba(0, 0, 0, 0.6); /* Added background color */
  color: white;
}

.card-info {
  padding: 0.625rem;
  font-size: 12px;
  text-shadow: rgba(0, 0, 0, 0.8) 0 1px 0;
}

.rating {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  align-self: stretch;
  bottom: 0;
}

.brewery-details {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-start;
  font-size: 12px;
  text-shadow: rgba(0, 0, 0, 0.8) 0 1px 0;
}

.name {
  margin: 0 0 0.5em;
  font-size: 20px;
  font-weight: 600;
  text-shadow: rgba(0, 0, 0, 0.8) 0 1px 0;
}

.footer {
  display: flex;
  color: #e7711b;
  text-align: left;
  width: 100%;
  margin-top: 2rem;
  margin-left: 0.625rem;
  margin-bottom: 0.3rem;
}

.icon-star-empty {
  margin-right: 5px;
  color: #ffb400;
  font-weight: 800;
  font-size: 16px;
}
.icon-star {
  margin-right: 10px;
  color: #ffb400;
  font-weight: 800;
  font-size: 16px;
}
.star-ratings {
  unicode-bidi: bidi-override;
  color: #ccc;
  font-size: 30px;
  position: relative;
  margin: 0;
  padding: 0;
}
.no-review {
  color: #ccc;
  position: relative;
  font-size: 2rem;
  padding-bottom: .5rem;
}
.no-review-text {
  font-size: 1rem;
  color: #e7711b;
  padding-top: 0.1;
}

.fill-ratings {
  color: #e7711b;
  padding: 0;
  position: absolute;
  display: block;
  top: 0;
  left: 0;
  overflow: hidden;
}
.filled-hearts {
  background-image: linear-gradient(red, red);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  position: absolute;
  top: 0;
  left: 250px;
}
.empty-hearts {
  position: absolute;
  top: 0;
  left: 250px;
}

span {
  display: inline-block;
}

.empty-ratings {
  padding: 0;
  display: block;
  z-index: 0;
}
</style>


