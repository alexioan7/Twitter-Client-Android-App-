

import tweepy
import numpy as np
import webbrowser
import time
import sys
import queue


from collections import deque



consumer_key = 'X49WPfKJ3lJixxzCVB8KuJK7z'
consumer_secret = 'bqbzunS3mKqNMQCnnlv5e2T5RkLmw9Ckzs2XFWeBODSWXU49yC'


class Frontier():
    """ Objects of this class keep track of the Breadth-First Search
    Frontier as it is expanded.
    """

    def __init__(self, src, expander):
        self.internal = set()
        self.perimeter = set()

        # The BFS queue contains the same elements as self.perimeter
        # but in bfs order, i.e. elements closer to source are nearer
        # to the beginning of the queue.
        self.queue = queue.Queue()

        self.perimeter.add(src)
        self.queue.put_nowait(src)

        # some metadata to be able to trace back the path from a node
        # to the source node
        self.metadata = {}
        self.metadata[src] = {"distance": 0, "parent": None}

        # This function is called on a node when we need to get the
        # outgoing edges. It is going to be different for the source
        # and destination nodes in our social graph. Returns a set.
        self.expander = expander

    def expand_perimeter(self):
        """ Expands the pperimeter by picking up the node at the front
        of the queue and expanding it to its followers/friends depending
        upon the definition of this.expander function.

        Returns the new elements found outside the periphery.
        """
        # User at the front of the breadth-first queue is made internal
        u = self.queue.get_nowait()
        self.internal.add(u)
        self.perimeter.remove(u)

        # let's move the frontier forward at the node 'u'
        new_nodes = self.expander(u).difference(self.internal, self.perimeter)
        print(".", end="")
        sys.stdout.flush()

        # Keep track of distance of a node from src and its parent
        d = self.metadata[u]["distance"]
        for n in new_nodes:
            self.metadata[n] = {"distance": d + 1, "parent": u}

        self.perimeter.update(new_nodes)
        list(map(self.queue.put_nowait, new_nodes))

        return set(new_nodes)

    def is_on_perimeter(self, user_id):
        """ Tells whether user_id is in on the perimeter of this bfs frontier.
        """
        return (user_id in self.perimeter)

    def covered_all(self):
        """ True if we have covered all the graph, i.e.
        The whole graph is now internal. There remain no nodes on
        the periphery.
        """
        return bool(self.perimeter)

    def get_parent(self, n):
        """ Returns the parent for node n.
        Will throw KeyError when we haven't seen the node before.
        But in this application, we don't expect that to happen. So, if it
        happens, something is really messed up.
        """
        return self.metadata[n]["parent"]

    def get_distance(self, n):
        """" Returns the distance of node `n` from the source.
        """
        return self.metadata[n]["distance"]


def main (source,destination,access_token, access_token_secret):

    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    error = -1;
    try:

        auth.set_access_token(access_token, access_token_secret)

    except tweepy.TweepError as e:
        print("Something went wrong! Couldn't authenticate.")
        print(e)
        return error;
        ##sys.exit(1)



    counter = 0
    while True:
        if counter == 1:
            break
        print()

        # source = source
        # destination = destination
        api = tweepy.API(auth)
        counter +=1


        if (source == destination):
            separation =-1
            print("Both users are the same!")
            ##exit(0)
            break

        try:
            t1 = time.perf_counter()
            # Get user ids from the user handles
            src_user = api.get_user(source)
            dest_user = api.get_user(destination)

            print("Begin: " + src_user.name)
            print("End: " + dest_user.name)
            print("Each dot is an api call that counts to the rate limit.")

            src_frontier = Frontier(src_user.id, lambda n: set(api.friends_ids(n)))
            dest_frontier = Frontier(dest_user.id, lambda n: set(api.followers_ids(n)))

            while src_frontier.covered_all() or dest_frontier.covered_all():
                # Expand the source node's frontier first
                nodes = src_frontier.expand_perimeter()

                # check if any one of new nodes is on the destination's perimeter
                if any(map(dest_frontier.is_on_perimeter, nodes)):
                    print("Found!")
                    break

                # Copy twice with a slight pain. If you have to copy thrice, abstract!
                nodes = dest_frontier.expand_perimeter()
                if any(map(src_frontier.is_on_perimeter, nodes)):
                    print("Found!")
                    break

            # The man in the middle!
            m = src_frontier.perimeter.intersection(dest_frontier.perimeter).pop()

            separation = src_frontier.get_distance(m) + dest_frontier.get_distance(m)




            # To collect the users in between src_node and dest_node
            mediators = deque()
            u = m
            v = m

            while u != src_user.id and u != dest_user.id:
                user = api.get_user(u)
                mediators.appendleft(user)
                u = src_frontier.get_parent(u)

            while v != dest_user.id and v != src_user.id:
                # Do not double copy the mediator.
                if v == m:
                    pass
                else:
                    user = api.get_user(v)
                    mediators.append(user)
                v = dest_frontier.get_parent(v)

            print(time.perf_counter() - t1)
            # Let's print the sequence of users now!
            print(src_user.name + " ??? ", end="")
            for user in mediators:
                print(user.name + " ??? ", end="")
            print(dest_user.name)

        except tweepy.RateLimitError:
            print("""It seems we have exceeded twitter's api call limit.
                     Please come back after 15 minutes.""")
            ##separation = -1
            ##returin separation;
            return error
            ##sys.exit(1)

        except tweepy.TweepError as e:
            print("Something went wrong.")
            print(e)
            return error
            ##separation = -1
            ##return separation
            ##sys.exit(1)

    if (separation > -1):
        print("Separation: {0}".format(separation))
        return separation
    else:
        return error
    
    
        

    
