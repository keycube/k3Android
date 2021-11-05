package be.keycu.k3android;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class K3Utils {

    private static final String TAG = K3Utils.class.getSimpleName();

    private static Map<String, Boolean> keyState;

    public static ArrayList<String> phraseSet;

    public static float WordsPerMinute(String transcribedText, float timing) {
        return (transcribedText.length()-1f)/timing*12f; // 60 * 1/5
    }

    public static float KSPC(int keystrokeCount, int transcribedTextLength) {
        return (float)keystrokeCount/transcribedTextLength;
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int LeveinshteinDistance(String x, String y) {
        return LeveinshteinMatrix(x, y)[x.length()][y.length()];
    }

    public static int[][] LeveinshteinMatrix(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1]
                            + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp;
    }

    public static float ErrorRate(String presentedText, String transcribedText)
    {
        return LeveinshteinDistance(presentedText, transcribedText) / (float) Math.max(presentedText.length(), transcribedText.length());
    }

    public static void RandomizePhraseSet()
    {
        if (phraseSet == null)
            InitPhraseSet();
        Collections.shuffle(phraseSet);
    }

    public static void InitPhraseSet2()
    {
        phraseSet = new ArrayList<String>(
                Arrays.asList(
                        "Are you going to join us for lunch?",
                        "Is she done yet?",
                        "Thanks for the quick turnaround.",
                        "How are you?",
                        "Yes, I am playing.",
                        "Please call tomorrow if possible.",
                        "We are all fragile.",
                        "I would like to attend if so.",
                        "I can return earlier.",
                        "I am trying again.",
                        "I will bring John Brindle.",
                        "He would love anything about rocks.",
                        "What do you hear?",
                        "Hope your trip to Florida was good.",
                        "What's his problem?",
                        "She called and wants to come over this AM.",
                        "There is now a meeting at 8PM as well.",
                        "See you soon!",
                        "It reads like she is in.",
                        "Has Dynegy made a specific request?",
                        "I am walking in now.",
                        "They have capacity now.",
                        "A gift isn't necessary.",
                        "Tell her to get my expense report done.",
                        "I am out of town on business tonight.",
                        "I'm waiting until she comes home.",
                        "Not even close.",
                        "Chris Foster is in!",
                        "They are more efficiently pooled.",
                        "Could you try ringing her?",
                        "Do you need it today?",
                        "Keep me posted!",
                        "John this message concerns me.",
                        "Call me to give me a heads up.",
                        "And leave my school alone.",
                        "What is in the plan?",
                        "Where do you want to meet to walk over there?",
                        "I am almost speechless.",
                        "Ava, please put me on the list.",
                        "Suggest you get facts before judging anyone.",
                        "Have I mentioned how much I love Houston traffic?",
                        "Take what you can get.",
                        "Should systems manage the migration?",
                        "I think that is the right answer.",
                        "I'm glad you liked it.",
                        "This looks fine.",
                        "I've never worked with her.",
                        "Get with Mary for format.",
                        "I hope you are feeling better.",
                        "I'm glad she likes her tree.",
                        "Are you getting all the information you need?",
                        "Have a great trip.",
                        "Did you talk to Ava this morning?",
                        "Can you help?",
                        "It's not looking too good is it?",
                        "Has anyone else heard anything?",
                        "Is it over?",
                        "I'll get you one.",
                        "OK with me.",
                        "What's going on?",
                        "You can talk to Becky!",
                        "I talked to Duran.",
                        "I agreed terms with Greg.",
                        "I am at the lake.",
                        "I told you silly.",
                        "Wednesday is definitely a hot chocolate day.",
                        "Thanks for your concern.",
                        "Thursday works better for me.",
                        "What is the mood?",
                        "I am on my way.",
                        "Do we need to discuss?",
                        "Just playing with you!",
                        "What's your phone number?",
                        "Thanks for checking with me.",
                        "This is very sensitive.",
                        "Can we have them until we move?",
                        "On the plane, doors closing.",
                        "I'll catch up with you tomorrow.",
                        "Are you in today?",
                        "Let it rip.",
                        "We just need a sitter.",
                        "We must be consistent.",
                        "She has absolutely everything.",
                        "I'm not planning on doing anything this week.",
                        "This is good I think.",
                        "We can have wine and catch up.",
                        "Don't they have some conflicts here?",
                        "Money wise that is.",
                        "What is wrong?",
                        "Where are you?",
                        "Thanks good job.",
                        "Hopefully this can wait until Monday.",
                        "No employment claims for gas or power.",
                        "Why do you ask?",
                        "Mike, are you aware of this?",
                        "I agree since I am at the bank right now.",
                        "I was planning to attend.",
                        "That would be great.",
                        "Thank you for your prompt reply.",
                        "Can you help me here?",
                        "I changed that in one prior draft.",
                        "What is the cost issue?",
                        "Please send me an email.",
                        "What a jerk.",
                        "Don't make me pull tapes on whether you understood our fee.",
                        "I wanted to go drinking with you.",
                        "No material impact.",
                        "We don't seem to have any positive income there.",
                        "I will be back Friday.",
                        "If not can I call you?",
                        "Do you still need me to sign something?",
                        "What's your proposal?",
                        "Can we meet at 3:30?",
                        "Both of us are still here.",
                        "Not even in yet.",
                        "Disney was great and I've been to eight baseball games.",
                        "How soon do you need it?",
                        "What number should he call you on?",
                        "Ava, do we need to worry about this?",
                        "Are you feeling better?",
                        "You have a nice holiday too.",
                        "We need to talk about this month.",
                        "What about Jay?",
                        "We are waiting on the cold front.",
                        "Ken agreed yesterday.",
                        "Neil has been asking around.",
                        "Are you available?",
                        "That would likely be an expensive option.",
                        "Good for you.",
                        "We will keep you posted.",
                        "Do we have anyone in Portland?",
                        "No surprise there.",
                        "Hope you guys are doing fine.",
                        "Are you going to call?",
                        "Did that happen?",
                        "I would be glad to participate.",
                        "I worked on the grade level promotion.",
                        "I have a request.",
                        "You're the greatest.",
                        "What is this?",
                        "Travis is in charge.",
                        "Can you handle?",
                        "Their key decision maker did not show which is not a good sign.",
                        "Can you help get this cleared up?",
                        "I have a high level in my office.",
                        "Thanks I will.",
                        "If we don't get it, could be trouble.",
                        "Are you being a baby?",
                        "Did you get this?",
                        "I'm on a plane.",
                        "Florida is great.",
                        "I sent it to her.",
                        "I will call.",
                        "Please let me know if you learn anything at the floor meeting.",
                        "Please revise accordingly.",
                        "I don't have the distraction of taking care of Mimi.",
                        "Could you see where this stands?",
                        "I'm going to class.",
                        "See you on the third.",
                        "Did we get ours back?",
                        "What is up with ENE?",
                        "I'll call you in the morning.",
                        "Are you sure?",
                        "Sorry about that!",
                        "Is that OK?",
                        "Jan has a lot of detail.",
                        "Need to watch closely.",
                        "What do you think?",
                        "I should have more info by our meeting this afternoon.",
                        "Can you bring these to 49C1?",
                        "Are you there?",
                        "I can review afterwards and get back to you tonight.",
                        "I hope he is having a fantastic time.",
                        "Can you resend me the Doyle email from last week?",
                        "If so what was it?",
                        "I'm in Stan's office.",
                        "Hey TK, how are you doing?",
                        "Don't forget the wood.",
                        "This seems fine to me.",
                        "What a pain.",
                        "Pressure to finish my review!",
                        "I like it.",
                        "I have 30 minutes then.",
                        "Will it be delivered?",
                        "Was wondering if you and Natalie connected?",
                        "Not at this time.",
                        "I'm still here.",
                        "We will get you a copy.",
                        "I will follow up with him as soon as the dust settles.",
                        "We're on the way.",
                        "Or are you going to be tied up with dinner?",
                        "Is this the only time available?",
                        "No there will be plenty of others.",
                        "What is the purpose of this?",
                        "No can do.",
                        "Nice weather for it.",
                        "I think those are the right dates.",
                        "Thai sounds good.",
                        "Do you want to fax it to my hotel?",
                        "Did you differ from me?"
                )
        );
    }

    private static void InitPhraseSet()
    {
        phraseSet = new ArrayList<String>(
                Arrays.asList(
                        "my watch fell in the water",
                        "prevailing wind from the east",
                        "never too rich and never too thin",
                        "breathing is difficult",
                        "i can see the rings on saturn",
                        "physics and chemistry are hard",
                        "my bank account is overdrawn",
                        "elections bring out the best",
                        "we are having spaghetti",
                        "time to go shopping",
                        "a problem with the engine",
                        "elephants are afraid of mice",
                        "my favorite place to visit",
                        "three two one zero blast off",
                        "my favorite subject is psychology",
                        "circumstances are unacceptable",
                        "watch out for low flying objects",
                        "if at first you do not succeed",
                        "please provide your date of birth",
                        "we run the risk of failure",
                        "prayer in schools offends some",
                        "he is just like everyone else",
                        "great disturbance in the force",
                        "love means many things",
                        "you must be getting old",
                        "the world is a stage",
                        "can i skate with sister today",
                        "neither a borrower nor a lender be",
                        "one heck of a question",
                        "question that must be answered",
                        "beware the ides of march",
                        "double double toil and trouble",
                        "the power of denial",
                        "i agree with you",
                        "do not say anything",
                        "play it again sam",
                        "the force is with you",
                        "you are not a jedi yet",
                        "an offer you cannot refuse",
                        "are you talking to me",
                        "yes you are very smart",
                        "all work and no play",
                        "hair gel is very greasy",
                        "valium in the economy size",
                        "the facts get in the way",
                        "the dreamers of dreams",
                        "did you have a good time",
                        "space is a high priority",
                        "you are a wonderful example",
                        "do not squander your time",
                        "do not drink too much",
                        "take a coffee break",
                        "popularity is desired by all",
                        "the music is better than it sounds",
                        "starlight and dewdrop",
                        "the living is easy",
                        "fish are jumping",
                        "the cotton is high",
                        "drove my chevy to the levee",
                        "but the levee was dry",
                        "i took the rover from the shop",
                        "movie about a nutty professor",
                        "come and see our new car",
                        "coming up with killer sound bites",
                        "i am going to a music lesson",
                        "the opposing team is over there",
                        "soon we will return from the city",
                        "i am wearing a tie and a jacket",
                        "the quick brown fox jumped",
                        "all together in one big pile",
                        "wear a crown with many jewels",
                        "there will be some fog tonight",
                        "i am allergic to bees and peanuts",
                        "he is still on our team",
                        "the dow jones index has risen",
                        "my preferred treat is chocolate",
                        "the king sends you to the tower",
                        "we are subjects and must obey",
                        "mom made her a turtleneck",
                        "goldilocks and the three bears",
                        "we went grocery shopping",
                        "the assignment is due today",
                        "what you see is what you get",
                        "for your information only",
                        "a quarter of a century",
                        "the store will close at ten",
                        "head shoulders knees and toes",
                        "vanilla flavored ice cream",
                        "frequently asked questions",
                        "round robin scheduling",
                        "information super highway",
                        "my favorite web browser",
                        "the laser printer is jammed",
                        "all good boys deserve fudge",
                        "the second largest country",
                        "call for more details",
                        "just in time for the party",
                        "have a good weekend",
                        "video camera with a zoom lens",
                        "what a monkey sees a monkey will do",
                        "that is very unfortunate",
                        "the back yard of our house",
                        "this is a very good idea",
                        "reading week is just about here",
                        "our fax number has changed",
                        "thank you for your help",
                        "no exchange without a bill",
                        "the early bird gets the worm",
                        "buckle up for safety",
                        "this is too much to handle",
                        "protect your environment",
                        "world population is growing",
                        "the library is closed today",
                        "mary had a little lamb",
                        "teaching services will help",
                        "we accept personal checks",
                        "this is a non profit organization",
                        "user friendly interface",
                        "healthy food is good for you",
                        "hands on experience with a job",
                        "this watch is too expensive",
                        "the postal service is very slow",
                        "communicate through email",
                        "the capital of our nation",
                        "travel at the speed of light",
                        "i do not fully agree with you",
                        "gas bills are sent monthly",
                        "earth quakes are predictable",
                        "life is but a dream",
                        "take it to the recycling depot",
                        "sent this by registered mail",
                        "fall is my favorite season",
                        "a fox is a very smart animal",
                        "the kids are very excited",
                        "parking lot is full of trucks",
                        "my bike has a flat tire",
                        "do not walk too quickly",
                        "a duck quacks to ask for food",
                        "limited warranty of two years",
                        "the four seasons will come",
                        "the sun rises in the east",
                        "it is very windy today",
                        "do not worry about this",
                        "dashing through the snow",
                        "want to join us for lunch",
                        "stay away from strangers",
                        "accompanied by an adult",
                        "see you later alligator",
                        "make my day you sucker",
                        "i can play much better now",
                        "she wears too much makeup",
                        "my bare face in the wind",
                        "batman wears a cape",
                        "i hate baking pies",
                        "lydia wants to go home",
                        "win first prize in the contest",
                        "freud wrote of the ego",
                        "i do not care if you do that",
                        "always cover all the bases",
                        "nobody cares anymore",
                        "can we play cards tonight",
                        "get rid of that immediately",
                        "i watched blazing saddles",
                        "the sum of the parts",
                        "they love to yap about nothing",
                        "peek out the window",
                        "be home before midnight",
                        "he played a pimp in that movie",
                        "i skimmed through your proposal",
                        "he was wearing a sweatshirt",
                        "no more war no more bloodshed",
                        "toss the ball around",
                        "i will meet you at noon",
                        "i want to hold your hand",
                        "the children are playing",
                        "superman never wore a mask",
                        "i listen to the tape everyday",
                        "he is shouting loudly",
                        "correct your diction immediately",
                        "seasoned golfers love the game",
                        "he cooled off after she left",
                        "my dog sheds his hair",
                        "join us on the patio",
                        "these cookies are so amazing",
                        "i can still feel your presence",
                        "the dog will bite you",
                        "a most ridiculous thing",
                        "where did you get that tie",
                        "what a lovely red jacket",
                        "do you like to shop on sunday",
                        "i spilled coffee on the carpet",
                        "the largest of the five oceans",
                        "shall we play a round of cards",
                        "olympic athletes use drugs",
                        "my mother makes good cookies",
                        "do a good deed to someone",
                        "quick there is someone knocking",
                        "flashing red light means stop",
                        "sprawling subdivisions are bad",
                        "where did i leave my glasses",
                        "on the way to the cottage",
                        "a lot of chlorine in the water",
                        "do not drink the water",
                        "my car always breaks in the winter",
                        "santa claus got stuck",
                        "public transit is much faster",
                        "zero in on the facts",
                        "make up a few more phrases",
                        "my fingers are very cold",
                        "rain rain go away",
                        "bad for the environment",
                        "universities are too expensive",
                        "the price of gas is high",
                        "the winner of the race",
                        "we drive on parkways",
                        "we park in driveways",
                        "go out for some pizza and beer",
                        "effort is what it will take",
                        "where can my little dog be",
                        "if you were not so stupid",
                        "not quite so smart as you think",
                        "do you like to go camping",
                        "this person is a disaster",
                        "the imagination of the nation",
                        "universally understood to be wrong",
                        "listen to five hours of opera",
                        "an occasional taste of chocolate",
                        "victims deserve more redress",
                        "the protesters blocked all traffic",
                        "the acceptance speech was boring",
                        "work hard to reach the summit",
                        "a little encouragement is needed",
                        "stiff penalty for staying out late",
                        "the pen is mightier than the sword",
                        "exceed the maximum speed limit",
                        "in sharp contrast to your words",
                        "this leather jacket is too warm",
                        "consequences of a wrong turn",
                        "this mission statement is baloney",
                        "you will loose your voice",
                        "every apple from every tree",
                        "are you sure you want this",
                        "the fourth edition was better",
                        "this system of taxation",
                        "beautiful paintings in the gallery",
                        "a yard is almost as long as a meter",
                        "we missed your birthday",
                        "coalition governments never work",
                        "destruction of the rain forest",
                        "i like to play tennis",
                        "acutely aware of her good looks",
                        "you want to eat your cake",
                        "machinery is too complicated",
                        "a glance in the right direction",
                        "i just cannot figure this out",
                        "please follow the guidelines",
                        "an airport is a very busy place",
                        "mystery of the lost lagoon",
                        "is there any indication of this",
                        "the chamber makes important decisions",
                        "this phenomenon will never occur",
                        "obligations must be met first",
                        "valid until the end of the year",
                        "file all complaints in writing",
                        "tickets are very expensive",
                        "a picture is worth many words",
                        "this camera takes nice photographs",
                        "it looks like a shack",
                        "the dog buried the bone",
                        "the daring young man",
                        "this equation is too complicated",
                        "express delivery is very fast",
                        "i will put on my glasses",
                        "a touchdown in the last minute",
                        "the treasury department is broke",
                        "a good response to the question",
                        "well connected with people",
                        "the bathroom is good for reading",
                        "the generation gap gets wider",
                        "chemical spill took forever",
                        "prepare for the exam in advance",
                        "interesting observation was made",
                        "bank transaction was not registered",
                        "your etiquette needs some work",
                        "we better investigate this",
                        "stability of the nation",
                        "house with new electrical panel",
                        "our silver anniversary is coming",
                        "the presidential suite is very busy",
                        "the punishment should fit the crime",
                        "sharp cheese keeps the mind sharp",
                        "the registration period is over",
                        "you have my sympathy",
                        "the objective of the exercise",
                        "historic meeting without a result",
                        "very reluctant to enter",
                        "good at addition and subtraction",
                        "six daughters and seven sons",
                        "a thoroughly disgusting thing to say",
                        "sign the withdrawal slip",
                        "relations are very strained",
                        "the minimum amount of time",
                        "a very traditional way to dress",
                        "the aspirations of a nation",
                        "medieval times were very hard",
                        "a security force of eight thousand",
                        "there are winners and losers",
                        "the voters turfed him out",
                        "pay off a mortgage for a house",
                        "the collapse of the roman empire",
                        "did you see that spectacular explosion",
                        "keep receipts for all your expenses",
                        "the assault took six months",
                        "get your priorities in order",
                        "traveling requires a lot of fuel",
                        "longer than a football field",
                        "a good joke deserves a good laugh",
                        "the union will go on strike",
                        "never mix religion and politics",
                        "interactions between men and women",
                        "where did you get such a silly idea",
                        "it should be sunny tomorrow",
                        "a psychiatrist will help you",
                        "you should visit to a doctor",
                        "you must make an appointment",
                        "the fax machine is broken",
                        "players must know all the rules",
                        "a dog is the best friend of a man",
                        "would you like to come to my house",
                        "february has an extra day",
                        "do not feel too bad about it",
                        "this library has many books",
                        "construction makes traveling difficult",
                        "he called seven times",
                        "that is a very odd question",
                        "a feeling of complete exasperation",
                        "we must redouble our efforts",
                        "no kissing in the library",
                        "that agreement is rife with problems",
                        "vote according to your conscience",
                        "my favourite sport is racketball",
                        "sad to hear that news",
                        "the gun discharged by accident",
                        "one of the poorest nations",
                        "the algorithm is too complicated",
                        "your presentation was inspiring",
                        "that land is owned by the government",
                        "burglars never leave their business card",
                        "the fire blazed all weekend",
                        "if diplomacy does not work",
                        "please keep this confidential",
                        "the rationale behind the decision",
                        "the cat has a pleasant temperament",
                        "our housekeeper does a thorough job",
                        "her majesty visited our country",
                        "handicapped persons need consideration",
                        "these barracks are big enough",
                        "sing the gospel and the blues",
                        "he underwent triple bypass surgery",
                        "the hopes of a new organization",
                        "peering through a small hole",
                        "rapidly running short on words",
                        "it is difficult to concentrate",
                        "give me one spoonful of coffee",
                        "two or three cups of coffee",
                        "just like it says on the can",
                        "companies announce a merger",
                        "electric cars need big fuel cells",
                        "the plug does not fit the socket",
                        "drugs should be avoided",
                        "the most beautiful sunset",
                        "we dine out on the weekends",
                        "get aboard the ship is leaving",
                        "the water was monitored daily",
                        "he watched in astonishment",
                        "a big scratch on the tabletop",
                        "salesmen must make their monthly quota",
                        "saving that child was an heroic effort",
                        "granite is the hardest of all rocks",
                        "bring the offenders to justice",
                        "every saturday he folds the laundry",
                        "careless driving results in a fine",
                        "microscopes make small things look big",
                        "a coupon for a free sample",
                        "fine but only in moderation",
                        "a subject one can really enjoy",
                        "important for political parties",
                        "that sticker needs to be validated",
                        "the fire raged for an entire month",
                        "one never takes too many precautions",
                        "we have enough witnesses",
                        "labour unions know how to organize",
                        "people blow their horn a lot",
                        "a correction had to be published",
                        "i like baroque and classical music",
                        "the proprietor was unavailable",
                        "be discreet about your meeting",
                        "meet tomorrow in the lavatory",
                        "suburbs are sprawling up everywhere",
                        "shivering is one way to keep warm",
                        "dolphins leap high out of the water",
                        "try to enjoy your maternity leave",
                        "the ventilation system is broken",
                        "dinosaurs have been extinct for ages",
                        "an inefficient way to heat a house",
                        "the bus was very crowded",
                        "an injustice is committed every day",
                        "the coronation was very exciting",
                        "look in the syllabus for the course",
                        "rectangular objects have four sides",
                        "prescription drugs require a note",
                        "the insulation is not working",
                        "nothing finer than discovering a treasure",
                        "our life expectancy has increased",
                        "the cream rises to the top",
                        "the high waves will swamp us",
                        "the treasurer must balance her books",
                        "completely sold out of that",
                        "the location of the crime",
                        "the chancellor was very boring",
                        "the accident scene is a shrine for fans",
                        "a tumor is ok provided it is benign",
                        "please take a bath this month",
                        "rent is paid at the beginning of the month",
                        "for murder you get a long prison sentence",
                        "a much higher risk of getting cancer",
                        "quit while you are ahead",
                        "knee bone is connected to the thigh bone",
                        "safe to walk the streets in the evening",
                        "luckily my wallet was found",
                        "one hour is allotted for questions",
                        "so you think you deserve a raise",
                        "they watched the entire movie",
                        "good jobs for those with education",
                        "jumping right out of the water",
                        "the trains are always late",
                        "sit at the front of the bus",
                        "do you prefer a window seat",
                        "the food at this restaurant",
                        "canada has ten provinces",
                        "the elevator door appears to be stuck",
                        "raindrops keep falling on my head",
                        "spill coffee on the carpet",
                        "an excellent way to communicate",
                        "with each step forward",
                        "faster than a speeding bullet",
                        "wishful thinking is fine",
                        "nothing wrong with his style",
                        "arguing with the boss is futile",
                        "taking the train is usually faster",
                        "what goes up must come down",
                        "be persistent to win a strike",
                        "presidents drive expensive cars",
                        "the stock exchange dipped",
                        "why do you ask silly questions",
                        "that is a very nasty cut",
                        "what to do when the oil runs dry",
                        "learn to walk before you run",
                        "insurance is important for bad drivers",
                        "traveling to conferences is fun",
                        "do you get nervous when you speak",
                        "pumping helps if the roads are slippery",
                        "parking tickets can be challenged",
                        "apartments are too expensive",
                        "find a nearby parking spot",
                        "gun powder must be handled with care",
                        "just what the doctor ordered",
                        "a rattle snake is very poisonous",
                        "weeping willows are found near water",
                        "i cannot believe i ate the whole thing",
                        "the biggest hamburger i have ever seen",
                        "gamblers eventually loose their shirts",
                        "exercise is good for the mind",
                        "irregular verbs are the hardest to learn",
                        "they might find your comment offensive",
                        "tell a lie and your nose will grow",
                        "an enlarged nose suggests you are a liar",
                        "lie detector tests never work",
                        "do not lie in court or else",
                        "most judges are very honest",
                        "only an idiot would lie in court",
                        "important news always seems to be late",
                        "please try to be home before midnight",
                        "if you come home late the doors are locked",
                        "dormitory doors are locked at midnight",
                        "staying up all night is a bad idea",
                        "you are a capitalist pig",
                        "motivational seminars make me sick",
                        "questioning the wisdom of the courts",
                        "rejection letters are discouraging",
                        "the first time he tried to swim",
                        "that referendum asked a silly question",
                        "a steep learning curve in riding a unicycle",
                        "a good stimulus deserves a good response",
                        "everybody looses in custody battles",
                        "put garbage in an abandoned mine",
                        "employee recruitment takes a lot of effort",
                        "experience is hard to come by",
                        "everyone wants to win the lottery",
                        "the picket line gives me the chills")
        );
    }

    private static void InitKeyState()
    {
        keyState = new HashMap<>();

        // blue
        keyState.put("+u0", false); //

        keyState.put("+u1", false);
        keyState.put("+u2", false);
        keyState.put("+u3", false);

        keyState.put("+u4", false); //

        keyState.put("+u5", false);
        keyState.put("+u6", false);
        keyState.put("+u7", false);

        keyState.put("+u8", false); //

        keyState.put("+u9", false);
        keyState.put("+uA", false);
        keyState.put("+uB", false);

        keyState.put("+uC", false); //

        keyState.put("+uD", false);
        keyState.put("+uE", false);
        keyState.put("+uF", false);

        // yellow
        keyState.put("+y0", false);
        keyState.put("+y1", false);
        keyState.put("+y2", false);
        keyState.put("+y3", false);
        keyState.put("+y4", false);
        keyState.put("+y5", false);
        keyState.put("+y6", false);
        keyState.put("+y7", false);
        keyState.put("+y8", false);
        keyState.put("+y9", false);
        keyState.put("+yA", false);

        keyState.put("+yB", false); //
        keyState.put("+yC", false); //
        keyState.put("+yD", false); //
        keyState.put("+yE", false); //
        keyState.put("+yF", false); //

        // red
        keyState.put("+r0", false); //

        keyState.put("+r1", false);
        keyState.put("+r2", false);

        keyState.put("+r3", false); //
        keyState.put("+r4", false); //
        keyState.put("+r5", false); //
        keyState.put("+r6", false); //

        keyState.put("+r7", false);

        keyState.put("+r8", false); //
        keyState.put("+r9", false); //
        keyState.put("+rA", false); //

        keyState.put("+rB", false);

        keyState.put("+rC", false); //
        keyState.put("+rD", false); //
        keyState.put("+rE", false); //
        keyState.put("+rF", false); //

        // green
        keyState.put("+g0", false); //
        keyState.put("+g1", false); //
        keyState.put("+g2", false); //
        keyState.put("+g3", false); //
        keyState.put("+g4", false); //
        keyState.put("+g5", false); //
        keyState.put("+g6", false); //
        keyState.put("+g7", false); //
        keyState.put("+g8", false); //
        keyState.put("+g9", false); //
        keyState.put("+gA", false); //
        keyState.put("+gB", false); //
        keyState.put("+gC", false); //
        keyState.put("+gD", false); //
        keyState.put("+gE", false); //
        keyState.put("+gF", false); //

        // white
        keyState.put("+w0", false); //
        keyState.put("+w1", false); //
        keyState.put("+w2", false); //
        keyState.put("+w3", false); //
        keyState.put("+w4", false); //
        keyState.put("+w5", false); //
        keyState.put("+w6", false); //
        keyState.put("+w7", false); //
        keyState.put("+w8", false); //
        keyState.put("+w9", false); //
        keyState.put("+wA", false); //
        keyState.put("+wB", false); //
        keyState.put("+wC", false); //
        keyState.put("+wD", false); //
        keyState.put("+wE", false); //
        keyState.put("+wF", false); //
    }

    public static String GetCharacterFromKeycubeCode(String code)
    {
        if (keyState == null)
            InitKeyState();

        if (!keyState.containsKey(code))
        {
            Log.d(TAG,"Do not contains Key");
            return null;
        }

        if (keyState.get(code)) // if true it means we just release the key, therefore nothing to do
        {
            keyState.put(code, false);
            return null;
        }

        keyState.put(code, true);
        switch (code)
        {
            // blue
            case "+u1":
                return "w";
            case "+u2":
                return "q";
            case "+u3":
                return "a";

            case "+u5":
                return "x";
            case "+u6":
                return "s";
            case "+u7":
                return "z";

            case "+u9":
                return "c";
            case "+uA":
                return "d";
            case "+uB":
                return "e";

            case "+uD":
                return "v";
            case "+uE":
                return "f";
            case "+uF":
                return "r";

            // yellow
            case "+y0":
                return "u";
            case "+y1":
                return "i";
            case "+y2":
                return "o";
            case "+y3":
                return "p";
            case "+y4":
                return "j";
            case "+y5":
                return "k";
            case "+y6":
                return "l";
            case "+y7":
                return "m";
            case "+y8":
                return "b";
            case "+y9":
                return "n";

            // red
            case "+r0":
                return "_";
            case "+r4":
                return "_";
            case "+r8":
                return "_";
            case "+r1":
                return "g";
            case "+r2":
                return "t";
            case "+r7":
                return "y";
            case "+rB":
                return "h";

            case "+rC":
                return ">";
            case "+rD":
                return ">";
            case "+rE":
                return "<";
            case "+rF":
                return "<";
        }
        return code;
    }
}
